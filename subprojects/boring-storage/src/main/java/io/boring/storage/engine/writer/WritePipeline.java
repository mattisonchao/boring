package io.boring.storage.engine.writer;

import static java.util.Objects.requireNonNull;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.boring.storage.engine.api.WriteOptions;
import java.io.Closeable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WritePipeline implements Closeable, EventHandler<WriteOp> {
  private final List<Writer> writers = new ArrayList<>();
  private final Disruptor<WriteOp> window;

  public WritePipeline(PipelineOptions options) {
    requireNonNull(options);
    final var walWriterOptions = options.walWriterOptions();
    if (walWriterOptions != null) {
      writers.add(new WalWriter(walWriterOptions));
    }
    final var memWriterOptions = options.memWriterOptions();
    if (memWriterOptions != null) {
      writers.add(new MemWriter(options.memWriterOptions()));
    }
    if (writers.isEmpty()) {
      throw new IllegalArgumentException("Empty write pipeline is not allowed.");
    }
    window =
        new Disruptor<>(
            WriteOp::new,
            1024 * 1024,
            Executors.defaultThreadFactory(),
            ProducerType.MULTI,
            new YieldingWaitStrategy());
    window.handleEventsWith(this);
    window.start();
  }

  @Override
  public void close() {
    for (Writer writer : writers) {
      try {
        writer.close();
      } catch (Throwable ex) {
        logger
            .atError()
            .addKeyValue("writer", writer.getClass().getName())
            .log("Close writer encountered an exception.", ex);
      }
    }
  }

  public CompletableFuture<Void> writeAsync(
      byte[] key, ByteBuffer value, WriteOptions options, Executor executor) {
    Objects.requireNonNull(key);
    Objects.requireNonNull(options);
    final RingBuffer<WriteOp> rb = window.getRingBuffer();
    long sequence = rb.next();
    final var writeOp = window.get(sequence);
    final CompletableFuture<Void> f = new CompletableFuture<>();
    writeOp.setKey(key);
    writeOp.setValue(value);
    writeOp.setOptions(options);
    writeOp.setWriteFuture(f);
    writeOp.setExecutor(executor);
    rb.publish(sequence);
    return f;
  }

  private List<WriteOp> batch;

  @Override
  public void onEvent(WriteOp writeOp, long sequence, boolean endOfBatch) {
    if (batch == null) {
      batch = new ArrayList<>();
    }
    if (!endOfBatch) {
      batch.add(writeOp);
      return;
    }
    final List<WriteOp> writableDataset = batch;
    batch = new ArrayList<>();
    final Writer head = writers.get(0);
    CompletableFuture<Void> f = head.write(writableDataset);
    for (int i = 1; i < writers.size(); i++) {
      final Writer next = writers.get(i);
      f = f.thenCompose(__ -> next.write(writableDataset));
    }
    f.thenAccept(
        __ ->
            writableDataset.forEach(
                callbackWriteOp -> {
                  try {
                    callbackWriteOp
                        .getExecutor()
                        .execute(() -> callbackWriteOp.getWriteFuture().complete(null));
                  } catch (RejectedExecutionException ex) {
                    logger
                        .atError()
                        .addKeyValue("executorName", callbackWriteOp.getExecutor())
                        .addKeyValue("key", writeOp.getKey())
                        .log("Write callback executor has been shutdown.", ex);
                  }
                }));
  }

  private static final Logger logger = LoggerFactory.getLogger(WritePipeline.class);
}

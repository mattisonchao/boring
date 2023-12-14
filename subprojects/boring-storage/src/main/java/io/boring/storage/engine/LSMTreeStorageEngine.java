package io.boring.storage.engine;

import io.boring.storage.engine.api.StorageEngine;
import io.boring.storage.engine.api.WriteOptions;
import io.boring.storage.engine.writer.PipelineOptions;
import io.boring.storage.engine.writer.WritePipeline;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

final class LSMTreeStorageEngine implements StorageEngine {

  private final WritePipeline writePipeline;

  LSMTreeStorageEngine() {
    this.writePipeline = new WritePipeline(new PipelineOptions(null, null));
  }

  @Override
  public CompletableFuture<Void> writeAsync(
      byte[] key, ByteBuffer value, WriteOptions options, Executor executor) {
    return writePipeline.writeAsync(key, value, options, executor);
  }
}

package io.boring.storage.writer;

import io.boring.storage.engine.api.WriteOptions;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class WriteOp {
  private byte[] key;
  private ByteBuffer value;
  private WriteOptions options;
  private CompletableFuture<Void> writeFuture;
  private Executor executor;

  public byte[] getKey() {
    return key;
  }

  public void setKey(byte[] key) {
    this.key = key;
  }

  public ByteBuffer getValue() {
    return value;
  }

  public void setValue(ByteBuffer value) {
    this.value = value;
  }

  public WriteOptions getOptions() {
    return options;
  }

  public void setOptions(WriteOptions options) {
    this.options = options;
  }

  public CompletableFuture<Void> getWriteFuture() {
    return writeFuture;
  }

  public void setWriteFuture(CompletableFuture<Void> writeFuture) {
    this.writeFuture = writeFuture;
  }

  public Executor getExecutor() {
    return executor;
  }

  public void setExecutor(Executor executor) {
    this.executor = executor;
  }
}

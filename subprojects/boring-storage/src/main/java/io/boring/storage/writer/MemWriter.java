package io.boring.storage.writer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

final class MemWriter implements Writer {

  public MemWriter(MemWriterOptions options) {}

  @Override
  public void close() {}

  @Override
  public CompletableFuture<Void> write(List<WriteOp> op) {
    return null;
  }
}

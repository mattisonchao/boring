package io.boring.storage.writer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

final class WalWriter implements Writer {
  WalWriter(WalWriterOptions options) {}

  @Override
  public void close() {}

  @Override
  public CompletableFuture<Void> write(List<WriteOp> op) {
    return null;
  }
}

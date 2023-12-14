package io.boring.storage.writer;

import java.io.Closeable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Writer extends Closeable {

  // todo recycler
  CompletableFuture<Void> write(List<WriteOp> op);
}

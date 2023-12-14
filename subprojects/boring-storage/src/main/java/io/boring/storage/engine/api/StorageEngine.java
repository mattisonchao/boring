package io.boring.storage.engine.api;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface StorageEngine {

  CompletableFuture<Void> writeAsync(
      byte[] key, ByteBuffer value, WriteOptions options, Executor executor);
}

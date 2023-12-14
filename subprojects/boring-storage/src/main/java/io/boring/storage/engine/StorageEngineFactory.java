package io.boring.storage.engine;

import static java.util.Objects.requireNonNull;

import io.boring.storage.engine.api.EngineOptions;
import io.boring.storage.engine.api.StorageEngine;

/**
 * {@link StorageEngine} factory.
 *
 * <p>todo.. comment
 */
public final class StorageEngineFactory {

  /**
   * @param engineOptions
   * @return
   */
  public static StorageEngine createEngine(EngineOptions engineOptions) {
    requireNonNull(engineOptions);
    return new LSMTreeStorageEngine();
  }
}

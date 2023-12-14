package io.boring.storage.engine;

import io.boring.storage.engine.api.EngineOptions;
import io.boring.storage.engine.api.StorageEngine;

import static java.util.Objects.requireNonNull;

/**
 * {@link StorageEngine} factory.
 * <p>
 * todo.. comment
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

package io.boring.storage.engine.api;

public record EngineOptions(EngineType engineType) {
    public EngineOptions() {
        this(EngineType.LSM_TREE);
    }
}

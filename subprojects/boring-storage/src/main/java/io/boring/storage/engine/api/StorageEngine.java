package io.boring.storage.engine.api;

import io.boring.storage.engine.api.PutOptions;

import java.nio.ByteBuffer;

public interface StorageEngine {

    void put(byte[] key, ByteBuffer value, PutOptions options);
}

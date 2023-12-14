package io.boring.storage.engine;

import io.boring.storage.engine.api.PutOptions;
import io.boring.storage.engine.api.StorageEngine;

import java.nio.ByteBuffer;

final class LSMTreeStorageEngine implements StorageEngine {

    LSMTreeStorageEngine() {
    }

    @Override
    public void put(byte[] key, ByteBuffer value, PutOptions options) {

    }
}

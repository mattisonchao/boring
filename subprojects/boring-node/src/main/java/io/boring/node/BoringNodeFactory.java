package io.boring.node;

import io.boring.node.api.BoringNode;

import java.util.Objects;

public final class BoringNodeFactory {

    public static BoringNode create(BoringNodeOptions options) {
        Objects.requireNonNull(options);
        return new BoringNodeImpl(options);
    }
}

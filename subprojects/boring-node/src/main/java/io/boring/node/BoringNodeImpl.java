package io.boring.node;

import io.boring.node.api.BoringNode;

final class BoringNodeImpl implements BoringNode {
    private final BoringNodeOptions options;

    public BoringNodeImpl(BoringNodeOptions options) {
        this.options = options;
    }
}

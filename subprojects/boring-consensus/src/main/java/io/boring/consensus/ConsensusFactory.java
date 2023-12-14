package io.boring.consensus;

import io.boring.consensus.api.Consensus;

import java.util.Objects;

public final class ConsensusFactory {

    public static Consensus create(ConsensusOptions options) {
        Objects.requireNonNull(options);
        return new RaftConsensus(options);
    }
}

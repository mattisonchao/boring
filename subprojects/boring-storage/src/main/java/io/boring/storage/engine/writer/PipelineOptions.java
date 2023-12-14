package io.boring.storage.engine.writer;

public record PipelineOptions(
    WalWriterOptions walWriterOptions, MemWriterOptions memWriterOptions) {}

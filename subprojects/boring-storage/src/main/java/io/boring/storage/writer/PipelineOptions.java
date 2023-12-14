package io.boring.storage.writer;

public record PipelineOptions(
    WalWriterOptions walWriterOptions, MemWriterOptions memWriterOptions) {}

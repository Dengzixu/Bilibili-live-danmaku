package net.dengzixu.exception;

public class UnknownProtocolVersionException extends RuntimeException {
    public UnknownProtocolVersionException() {
        super("Unknown Protocol Version");
    }
}

package com.space.exception;

public class RequestException extends RuntimeException {
    public RequestException() {
        super();
    }

    public RequestException(String message) {
        super(message);
    }
}

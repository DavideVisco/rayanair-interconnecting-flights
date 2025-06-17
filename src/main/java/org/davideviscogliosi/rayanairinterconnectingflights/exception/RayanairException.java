package org.davideviscogliosi.rayanairinterconnectingflights.exception;

import org.springframework.http.HttpStatus;


public class RayanairException extends RuntimeException {

    private final HttpStatus status;

    public RayanairException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}

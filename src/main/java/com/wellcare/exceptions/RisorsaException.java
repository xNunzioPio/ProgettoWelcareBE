package com.wellcare.exceptions;

public class RisorsaException extends Exception {

    public static final long serialVersionUID=1L;

    public RisorsaException() {
    }

    public RisorsaException(String message) {
        super(message);
    }
    public RisorsaException(Throwable cause) {
        super(cause);
    }
}

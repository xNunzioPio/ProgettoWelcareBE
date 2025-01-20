package com.wellcare.exceptions;

public class RuoloException extends Exception {
    public static final long serialVersionUID=1L;

    public RuoloException() {
    }

    public RuoloException(String message) {
        super(message);
    }
    public RuoloException(Throwable cause) {
        super(cause);
    }
}

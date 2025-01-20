package com.wellcare.exceptions;

public class TipoRisorsaException extends Exception {

    public static final long serialVersionUID=1L;

    public TipoRisorsaException() {
    }

    public TipoRisorsaException(String message) {
        super(message);
    }
    public TipoRisorsaException(Throwable cause) {
        super(cause);
    }
}

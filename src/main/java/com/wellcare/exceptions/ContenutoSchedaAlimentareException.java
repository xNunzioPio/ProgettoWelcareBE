package com.wellcare.exceptions;

public class ContenutoSchedaAlimentareException extends Exception {

    public static final long serialVersionUID=1L;

    public ContenutoSchedaAlimentareException() {
    }

    public ContenutoSchedaAlimentareException(String message) {
        super(message);
    }
    public ContenutoSchedaAlimentareException(Throwable cause) {
        super(cause);
    }
}

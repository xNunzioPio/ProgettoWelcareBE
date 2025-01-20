package com.wellcare.exceptions;

public class SchedaAlimentareException extends Exception {

    public static final long serialVersionUID=1L;

    public SchedaAlimentareException() {
    }

    public SchedaAlimentareException(String message) {
        super(message);
    }
    public SchedaAlimentareException(Throwable cause) {
        super(cause);
    }
}

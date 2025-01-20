package com.wellcare.exceptions;

public class CiboException extends Exception {

    public static final long serialVersionUID=1L;

    public CiboException() {
    }

    public CiboException(String message) {
        super(message);
    }
    public CiboException(Throwable cause) {
        super(cause);
    }
}

package com.wellcare.exceptions;

public class ClienteException extends Exception{
    public static final long serialVersionUID=1L;

    public ClienteException() {
    }

    public ClienteException(String message) {
        super(message);
    }
    public ClienteException(Throwable cause) {
        super(cause);
    }
}

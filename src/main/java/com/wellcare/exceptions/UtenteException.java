package com.wellcare.exceptions;

public class UtenteException extends Exception{

    public static final long serialVersionUID=1L;

    public UtenteException() {
    }

    public UtenteException(String message) {
        super(message);
    }
    public UtenteException(Throwable cause) {
        super(cause);
    }
}

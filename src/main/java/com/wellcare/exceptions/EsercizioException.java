package com.wellcare.exceptions;

public class EsercizioException extends Exception{

    public static final long serialVersionUID=1L;

    public EsercizioException() {
    }

    public EsercizioException(String message) {
        super(message);
    }
    public EsercizioException(Throwable cause) {
        super(cause);
    }
}

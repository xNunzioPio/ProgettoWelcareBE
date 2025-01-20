package com.wellcare.exceptions;

public class EserciziGiornalieriException extends Exception{
    public static final long serialVersionUID=1L;

    public EserciziGiornalieriException() {
    }

    public EserciziGiornalieriException(String message) {
        super(message);
    }
    public EserciziGiornalieriException(Throwable cause) {
        super(cause);
    }
}

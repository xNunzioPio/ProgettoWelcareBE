package com.wellcare.exceptions;

public class NutrizionistaException extends Exception{

    public static final long serialVersionUID=1L;

    public NutrizionistaException() {
    }

    public NutrizionistaException(String message) {
        super(message);
    }
    public NutrizionistaException(Throwable cause) {
        super(cause);
    }
}

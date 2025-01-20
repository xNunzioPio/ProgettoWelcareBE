package com.wellcare.exceptions;

public class PastiGiornalieriException extends Exception {

    public static final long serialVersionUID=1L;

    public PastiGiornalieriException() {
    }

    public PastiGiornalieriException(String message) {
        super(message);
    }
    public PastiGiornalieriException(Throwable cause) {
        super(cause);
    }
}

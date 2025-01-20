package com.wellcare.exceptions;

public class ContenutoSchedaAllenamentoException extends Exception{

    public static final long serialVersionUID=1L;

    public ContenutoSchedaAllenamentoException() {
    }

    public ContenutoSchedaAllenamentoException(String message) {
        super(message);
    }
    public ContenutoSchedaAllenamentoException(Throwable cause) {
        super(cause);
    }
}

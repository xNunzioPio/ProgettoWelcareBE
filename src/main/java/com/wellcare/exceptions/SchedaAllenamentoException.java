package com.wellcare.exceptions;

public class SchedaAllenamentoException extends Exception{

    public static final long serialVersionUID=1L;

    public SchedaAllenamentoException() {
    }

    public SchedaAllenamentoException(String message) {
        super(message);
    }
    public SchedaAllenamentoException(Throwable cause) {
        super(cause);
    }
}

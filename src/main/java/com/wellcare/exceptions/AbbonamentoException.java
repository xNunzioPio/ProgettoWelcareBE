package com.wellcare.exceptions;

import java.io.Serializable;

public class AbbonamentoException extends Exception {

    public static final long serialVersionUID=1L;

    public AbbonamentoException() {
    }

    public AbbonamentoException(String message) {
        super(message);
    }
    public AbbonamentoException(Throwable cause) {
        super(cause);
    }
}

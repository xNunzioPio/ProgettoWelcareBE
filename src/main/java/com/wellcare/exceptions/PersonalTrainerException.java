package com.wellcare.exceptions;

public class PersonalTrainerException extends Exception{

    public static final long serialVersionUID=1L;

    public PersonalTrainerException() {
    }

    public PersonalTrainerException(String message) {
        super(message);
    }
    public PersonalTrainerException(Throwable cause) {
        super(cause);
    }
}

package com.learning.spring.petclinic.vet;

public class VetNotFoundException extends RuntimeException{

    public VetNotFoundException(String message) {
        super(message);
    }

    public VetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VetNotFoundException(Throwable cause) {
        super(cause);
    }

}

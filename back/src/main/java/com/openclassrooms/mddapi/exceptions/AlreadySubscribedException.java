package com.openclassrooms.mddapi.exceptions;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(String message) {
        super(message);
    }
}

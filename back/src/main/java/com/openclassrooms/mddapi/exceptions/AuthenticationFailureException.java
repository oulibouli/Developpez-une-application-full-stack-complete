package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when there is a failure in the authentication process.
 */
public class AuthenticationFailureException extends RuntimeException {
    public AuthenticationFailureException(String message) {
        super(message);
    }
}


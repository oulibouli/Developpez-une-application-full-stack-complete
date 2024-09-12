package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when an invalid JWT token is encountered.
 */
public class InvalidJwtTokenException extends RuntimeException{
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}

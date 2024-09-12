package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when the Authorization header is missing in the HTTP request.
 */
public class MissingAuthorizationHeaderException extends RuntimeException {
    public MissingAuthorizationHeaderException(String message) {
        super(message);
    }
}
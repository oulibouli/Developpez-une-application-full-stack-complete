package com.openclassrooms.mddapi.exceptions;

/**
 * Exception thrown when a user tries to subscribe to a topic they are already subscribed to.
 */
public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(String message) {
        super(message);
    }
}

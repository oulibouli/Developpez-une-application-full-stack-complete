package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * DTO for representing a Subscription.
 * Contains fields such as message, user, topic, and subscription status.
 */
@Data
public class SubscriptionDTO {
    private String message;
    private int id;
    private String user;
    private String topic;
    private boolean isActive;
}

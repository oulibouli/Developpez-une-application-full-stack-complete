package com.openclassrooms.mddapi.dto;

import lombok.Data;

/**
 * DTO for representing a Topic.
 * Contains fields for the topic title, description, and subscription status.
 */
@Data
public class TopicDTO {
    private int id;
    private String title;
    private String description;
    private boolean subscribed;
}

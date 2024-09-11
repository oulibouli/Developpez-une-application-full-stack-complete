package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class TopicDTO {
    private int id;
    private String title;
    private String description;
    private boolean subscribed;
}

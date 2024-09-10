package com.openclassrooms.mddapi.dto;

import lombok.Data;

@Data
public class SubscriptionDTO {
    private String error;
    private int id;
    private String user;
    private String topic;
    private boolean isActive;
}

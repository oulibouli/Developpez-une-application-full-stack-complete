package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTO {
    private int id;
    private String author;
    private String description;
    private LocalDateTime date;
}

package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO for representing comment data.
 * Contains information such as author, description, date, and post title.
 */
@Data
public class CommentDTO {
    private int id;
    private String author;
    private String description;
    private LocalDateTime date;
    private String postTitle;  // Au lieu de l'objet Post complet
}

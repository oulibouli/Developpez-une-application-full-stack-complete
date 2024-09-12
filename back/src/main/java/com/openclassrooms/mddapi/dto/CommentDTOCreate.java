package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO for creating a new comment.
 * Contains comment data including the author, description, date, and post ID.
 */
@Data
public class CommentDTOCreate {
    private int id;
    private int authorId;
    private String description;
    private LocalDateTime date;
    private int postId;
}

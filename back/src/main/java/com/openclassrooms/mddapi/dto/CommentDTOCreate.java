package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDTOCreate {
    private int id;
    private int authorId;
    private String description;
    private LocalDateTime date;
    private int postId;
}

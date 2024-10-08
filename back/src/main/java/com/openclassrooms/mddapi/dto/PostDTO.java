package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for representing a Post.
 * Contains post details such as title, topic name, content, author, and comments.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class PostDTO {

    private int id;
    @NotNull(message = "Cannot be null")
    private String title;

    private String topicName;

    private String content;

    private String author;

    private LocalDateTime date;
    private List<CommentDTO> comments;

}

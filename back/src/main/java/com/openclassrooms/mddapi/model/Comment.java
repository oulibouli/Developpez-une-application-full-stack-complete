package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="postid")
    private Long postId;
    // The field won't be in the database
    @JoinColumn(name="postid", insertable=false, updatable=false)
    private Post post;

    @Column(name="userid")
    private Long userId;
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private User user;

    private LocalDateTime date;
    private String description;
}

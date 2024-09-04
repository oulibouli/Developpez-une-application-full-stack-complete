package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    // The field won't be in the database
    @JoinColumn(name="postid", insertable=false, updatable=false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private User user;

    private LocalDateTime date;
    private String description;
}

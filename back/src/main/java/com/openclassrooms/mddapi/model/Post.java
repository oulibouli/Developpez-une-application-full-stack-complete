package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name="topicid")
    private Topic topic;

    private String content;

    @ManyToOne
    @JoinColumn(name="userid")
    private User user;

    private LocalDateTime date;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}

package com.openclassrooms.mddapi.model;

import java.time.LocalDateTime;

import javax.security.auth.Subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private Long id;

    private String title;

    @Column(name="subjectid")
    private Long subjectId;
    @JoinColumn(name="subjectid", insertable=false, updatable=false)
    private Subject subject;

    private String content;

    @Column(name="userid")
    private Long userId;
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private User user;

    private LocalDateTime date;
}

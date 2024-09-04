package com.openclassrooms.mddapi.model;

import javax.security.auth.Subject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="userid")
    private Long userId;
    @JoinColumn(name="userid", insertable=false, updatable=false)
    private User user;

    @Column(name="subjectid")
    private Long subjectId;
    @JoinColumn(name="subjectid", insertable=false, updatable=false)
    private Subject subject;

    @Column(name="isactive")
    private boolean isActive;
}

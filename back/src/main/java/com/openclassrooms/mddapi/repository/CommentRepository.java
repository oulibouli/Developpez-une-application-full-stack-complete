package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Comment;

/**
 * Repository interface for managing Comment entities.
 * Inherits from JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface CommentRepository extends  JpaRepository<Comment, Integer>{

}

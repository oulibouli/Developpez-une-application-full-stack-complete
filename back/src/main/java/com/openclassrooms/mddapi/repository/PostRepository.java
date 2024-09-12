package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;

/**
 * Repository interface for managing Post entities.
 * Inherits from JpaRepository to provide basic CRUD operations.
 * Includes a method to find all posts associated with a specific Topic.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    /**
     * Find all posts associated with a given topic.
     * 
     * @param topic the Topic entity to filter by.
     * @return a list of posts related to the topic.
     */
    @Query("SELECT p FROM Post p WHERE p.topic IN :topics")
    List<Post> findAllByTopics(@Param("topics") List<Topic> topics);
}

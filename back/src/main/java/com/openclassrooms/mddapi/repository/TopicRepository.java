package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Topic;

/**
 * Repository interface for managing Topic entities.
 * Inherits from JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

}

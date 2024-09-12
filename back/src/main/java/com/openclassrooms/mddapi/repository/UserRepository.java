package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.User;

/**
 * Repository interface for managing User entities.
 * Inherits from JpaRepository to provide basic CRUD operations.
 * Includes methods to find users by email and username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Find a user by their email address.
     * 
     * @param email the email address of the user.
     * @return an optional containing the user if found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by their username.
     * 
     * @param username the username of the user.
     * @return an optional containing the user if found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a user with the given email exists.
     * 
     * @param email the email address to check.
     * @return true if the user exists, otherwise false.
     */
    boolean existsByEmail(String email);

    /**
     * Check if a user with the given username exists.
     * 
     * @param username the username to check.
     * @return true if the user exists, otherwise false.
     */
    boolean existsByUsername(String username);
}

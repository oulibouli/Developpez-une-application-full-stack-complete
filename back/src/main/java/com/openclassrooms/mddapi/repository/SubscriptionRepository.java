package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

/**
 * Repository interface for managing Subscription entities.
 * Inherits from JpaRepository to provide basic CRUD operations.
 * Includes methods to find subscriptions by user and topic.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    /**
     * Find a subscription by user and topic.
     * 
     * @param user the User entity associated with the subscription.
     * @param topic the Topic entity associated with the subscription.
     * @return the subscription if found.
     */
    Subscription findByUserAndTopic(User user, Topic topic);

    /**
     * Find all active subscriptions for a specific user.
     * 
     * @param user the User entity associated with the subscriptions.
     * @return a list of active subscriptions.
     */
    List<Subscription> findByUserAndIsActiveIsTrue(User user);

    /**
     * Check if an active subscription exists for a user and topic.
     * 
     * @param user the User entity.
     * @param topic the Topic entity.
     * @return true if an active subscription exists, otherwise false.
     */
    Boolean existsByUserAndTopicAndIsActiveIsTrue(User user, Topic topic);
}

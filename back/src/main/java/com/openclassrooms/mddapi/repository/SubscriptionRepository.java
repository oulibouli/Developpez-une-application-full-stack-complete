package com.openclassrooms.mddapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findByUserAndTopic(User user, Topic topic);
    List<Subscription> findByUserAndIsActiveIsTrue(User user);
}

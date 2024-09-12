package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.SubscriptionDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.service.TopicService;

/**
 * REST controller for managing topics and subscriptions.
 */
@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * Retrieves all topics available to the authenticated user.
     *
     * @param userDetails the authenticated user.
     * @return a list of topics.
     */
    @GetMapping("")
    public ResponseEntity<List<TopicDTO>> getTopics(@AuthenticationPrincipal UserDetails userDetails) {
        return topicService.getTopics(userDetails);
    }

    /**
     * Subscribes the authenticated user to a topic.
     *
     * @param topicId the ID of the topic.
     * @param userDetails the authenticated user.
     * @return the subscription details.
     */
    @PostMapping("/subscribe/{topicId}")
    public ResponseEntity<SubscriptionDTO> subscribe(@PathVariable int topicId, @AuthenticationPrincipal UserDetails userDetails) {
        return topicService.subscribe(topicId, userDetails);
    }
    
    /**
     * Unsubscribes the authenticated user from a topic.
     *
     * @param topicId the ID of the topic.
     * @param userDetails the authenticated user.
     * @return the subscription details.
     */
    @PostMapping("/unsubscribe/{topicId}")
    public ResponseEntity<SubscriptionDTO> unsubscribe(@PathVariable int topicId, @AuthenticationPrincipal UserDetails userDetails) {
        return topicService.unsubscribe(topicId, userDetails);
    }

    /**
     * Retrieves the list of topics to which the authenticated user is subscribed.
     *
     * @param userDetails the authenticated user.
     * @return a list of subscribed topics.
     */
    @GetMapping("/subscriptions")
    public ResponseEntity<List<TopicDTO>> getSubscriptionsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        return topicService.getSubscriptionsByUser(userDetails);
    }
}

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


@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("")
    public ResponseEntity<List<TopicDTO>> getTopics() {
        return topicService.getTopics();
    }

    @PostMapping("/subscribe/{topicId}")
    public ResponseEntity<SubscriptionDTO> subscribe(@PathVariable int topicId, @AuthenticationPrincipal UserDetails userDetails) {
        return topicService.subscribe(topicId, userDetails);
    }
    
    @PostMapping("/unsubscribe/{topicId}")
    public ResponseEntity<SubscriptionDTO> unsubscribe(@PathVariable int topicId, @AuthenticationPrincipal UserDetails userDetails) {
        return topicService.unsubscribe(topicId, userDetails);
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<TopicDTO>> getSubscriptionsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        return topicService.getSubscriptionsByUser(userDetails);
    }
}

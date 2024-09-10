package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.SubscriptionDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicMapper;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public ResponseEntity<List<TopicDTO>> getTopics() {
        List<Topic> topics = topicRepository.findAll();

        List<TopicDTO> topicDTOs = topics.stream().map(topic -> {          
            return topicMapper.toDto(topic);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(topicDTOs);
    }

    public ResponseEntity<SubscriptionDTO> subscribe(Integer topicId, UserDetails userDetails) {
        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        try {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
            Topic topic = topicRepository.findById(topicId).orElseThrow();

            Subscription existingSubscription = subscriptionRepository.findByUserAndTopic(user, topic);
            
            Subscription subscription;

            if (existingSubscription != null) {
                if (!existingSubscription.isActive()) {
                    existingSubscription.setActive(true);
                    subscription = existingSubscription;
                } else {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "User is already subscribed to this topic.");
                }
            } else {
                subscription = new Subscription();
                subscription.setActive(true);
                subscription.setTopic(topic);
                subscription.setUser(user);
            }

            subscriptionRepository.save(subscription);

            subscriptionDTO.setActive(true);
            subscriptionDTO.setId(subscription.getId());
            subscriptionDTO.setTopic(subscription.getTopic().getTitle());
            subscriptionDTO.setUser(subscription.getUser().getEmail());

            return ResponseEntity.ok(subscriptionDTO);
        }
        catch (ResponseStatusException e) {
            subscriptionDTO.setError(e.getMessage());
            return new ResponseEntity<>(subscriptionDTO, HttpStatus.CONFLICT);
        }
    }
}

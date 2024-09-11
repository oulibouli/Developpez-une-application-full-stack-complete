package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.SubscriptionDTO;
import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicMapper;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

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
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new EntityNotFoundException("User not found"));

            Subscription existingSubscription = subscriptionRepository.findByUserAndTopic(user, topic);
            
            Subscription subscription = null;

            if (existingSubscription != null) {
                if (!existingSubscription.isActive()) {
                    existingSubscription.setActive(true);
                    subscription = existingSubscription;  // Assigne ici la subscription
                    subscriptionDTO.setMessage("Votre abonnement à ce thème à été réactivé");
                } else {
                    subscriptionDTO.setMessage("Vous êtes déjà abonné à ce thème");
                }
            } else {
                subscription = new Subscription();
                subscription.setActive(true);
                subscription.setTopic(topic);
                subscription.setUser(user);
                subscriptionDTO.setMessage("Votre abonnement à ce thème à été activé");
            }
            
            // Vérifie que `subscription` est non-nulle avant de la sauvegarder
            if (subscription != null) {
                subscriptionRepository.save(subscription);
                subscriptionDTO.setActive(true);
                subscriptionDTO.setId(subscription.getId());
                subscriptionDTO.setTopic(subscription.getTopic().getTitle());
                subscriptionDTO.setUser(subscription.getUser().getEmail());
            }

            return ResponseEntity.ok(subscriptionDTO);
        }
        catch (EntityNotFoundException e) {
            subscriptionDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(subscriptionDTO, HttpStatus.NOT_FOUND);
        }
    }
}

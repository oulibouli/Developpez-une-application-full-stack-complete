package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostDTOCreate;
import com.openclassrooms.mddapi.dto.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for handling post-related operations.
 * This includes creating, retrieving, and managing posts.
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    /**
     * Retrieve all posts for a user, including posts from their subscribed topics.
     * 
     * @param userDetails The authenticated user's details.
     * @return ResponseEntity containing a list of posts.
     */
    public ResponseEntity<List<PostDTO>> getAllPosts(UserDetails userDetails) {
        try {
            User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

            List<Subscription> subscriptions = subscriptionRepository.findByUserAndIsActiveIsTrue(user);

            List<Topic> topics = subscriptions.stream()
                .map(Subscription::getTopic)
                .collect(Collectors.toList());
            
            List<Post> posts = postRepository.findAllByTopics(topics);

            List<PostDTO> postDTOs = posts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

            return ResponseEntity.ok(postDTOs);
        }
        catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Retrieve a specific post by its ID.
     * 
     * @param id The ID of the post.
     * @return ResponseEntity containing the post.
     */
    public ResponseEntity<PostDTO> getPostById(int id) {
        try {
            Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
            return ResponseEntity.ok(postMapper.toDto(post));
        } catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding post : " + e.getMessage());
        }
    }

    /**
     * Create a new post under a specific topic.
     * 
     * @param postDTO The post creation data.
     * @param topicId The ID of the topic.
     * @param userDetails The authenticated user's details.
     * @return ResponseEntity containing the created post.
     */
    public ResponseEntity<PostDTO> createPost(PostDTOCreate postDTO, int topicId, UserDetails userDetails) {
        Post post = postMapper.toEntity(postDTO);
        Topic topic = topicRepository.findById(topicId).orElseThrow();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        post.setTopic(topic);
        post.setUser(user);
        
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok(postMapper.toDto(savedPost));
    }
}

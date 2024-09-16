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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing topics and subscriptions.
 */
@RestController
@RequestMapping("/api/topics")
// Swagger annotation
@Tag(name = "Topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * Retrieves all topics available to the authenticated user.
     *
     * @param userDetails the authenticated user.
     * @return a list of topics.
     */
    @Operation(summary = "Get all topics", description = "Retrieves all topics and indicates whether the user is subscribed to each topic.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Topics successfully retrieved.", content = @Content(schema = @Schema(implementation = TopicDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
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
    @Operation(summary = "Subscribe to a topic", description = "Subscribes the authenticated user to a specific topic.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription successfully activated.", content = @Content(schema = @Schema(implementation = SubscriptionDTO.class))),
        @ApiResponse(responseCode = "404", description = "User or topic not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
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
    @Operation(summary = "Unsubscribe from a topic", description = "Unsubscribes the authenticated user from a specific topic.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription successfully deactivated.", content = @Content(schema = @Schema(implementation = SubscriptionDTO.class))),
        @ApiResponse(responseCode = "404", description = "User or topic not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
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
    @Operation(summary = "Get subscribed topics", description = "Retrieves the list of topics that the authenticated user is subscribed to.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscribed topics successfully retrieved.", content = @Content(schema = @Schema(implementation = TopicDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/subscriptions")
    public ResponseEntity<List<TopicDTO>> getSubscriptionsByUser(@AuthenticationPrincipal UserDetails userDetails) {
        return topicService.getSubscriptionsByUser(userDetails);
    }
}

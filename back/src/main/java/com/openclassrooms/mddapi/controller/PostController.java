package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostDTOCreate;
import com.openclassrooms.mddapi.service.PostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing posts.
 */
@RestController
@RequestMapping("/api/posts")
// Swagger annotation
@Tag(name = "Post")
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * Retrieves all posts visible to the authenticated user.
     *
     * @param userDetails the authenticated user.
     * @return a list of posts.
     */
    @Operation(summary = "Get all posts", description = "Retrieves all posts for the authenticated user, including posts from their subscribed topics.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts successfully retrieved.", content = @Content(schema = @Schema(implementation = PostDTO.class))),
        @ApiResponse(responseCode = "404", description = "User not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("")
    public ResponseEntity<List<PostDTO>> getAllPosts(@AuthenticationPrincipal UserDetails userDetails) {
        return postService.getAllPosts(userDetails);
    }

    /**
     * Retrieves a specific post by ID.
     *
     * @param id the ID of the post.
     * @return the post details.
     */
    @Operation(summary = "Get post by ID", description = "Retrieves a specific post by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post successfully retrieved.", content = @Content(schema = @Schema(implementation = PostDTO.class))),
        @ApiResponse(responseCode = "404", description = "Post not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable int id) {
        return postService.getPostById(id);
    }

    /**
     * Creates a new post for a specific topic.
     *
     * @param postDTO the post details.
     * @param id the topic ID.
     * @param userDetails the authenticated user creating the post.
     * @return the created post.
     */
    @Operation(summary = "Create a new post", description = "Creates a new post under a specific topic.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post successfully created.", content = @Content(schema = @Schema(implementation = PostDTO.class))),
        @ApiResponse(responseCode = "404", description = "Topic or user not found."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/{id}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTOCreate postDTO, @PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postDTO, id, userDetails);
    }
}

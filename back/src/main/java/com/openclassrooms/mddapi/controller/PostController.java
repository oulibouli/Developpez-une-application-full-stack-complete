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

/**
 * REST controller for managing posts.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    /**
     * Retrieves all posts visible to the authenticated user.
     *
     * @param userDetails the authenticated user.
     * @return a list of posts.
     */
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
    @PostMapping("/{id}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTOCreate postDTO, @PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        return postService.createPost(postDTO, id, userDetails);
    }
}

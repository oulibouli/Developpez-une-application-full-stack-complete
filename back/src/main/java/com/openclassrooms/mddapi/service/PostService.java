package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        
        // Transformer les entités Post en PostDTO
        return posts.stream().map(post -> {          
            return postMapper.toDto(post);
        }).collect(Collectors.toList());
    }

    public ResponseEntity<PostDTO> getPostById(int id) {
        try {
            Post post = postRepository.findById(id)
                .orElseThrow(() -> new Exception("Post not found with id: " + id));
            return ResponseEntity.ok(postMapper.toDto(post));
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error finding post : " + e.getMessage());
        }
    }
}

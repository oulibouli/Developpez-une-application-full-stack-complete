package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        
        // Transformer les entités Post en PostDTO
        return posts.stream().map(post -> {
            PostDTO postDTO = new PostDTO();
            postDTO.setId(post.getId());
            postDTO.setTitle(post.getTitle());
            postDTO.setContent(post.getContent());
            postDTO.setAuthor(post.getUser().getEmail());  // Email de l'auteur du post
            postDTO.setDate(post.getDate());
            
            // Mapper les commentaires vers CommentDTO
            List<CommentDTO> commentDTOs = post.getComments().stream().map(comment -> {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setId(comment.getId());
                commentDTO.setDescription(comment.getDescription());
                commentDTO.setAuthor(comment.getUser().getEmail());  // Email de l'auteur du commentaire
                commentDTO.setDate(comment.getDate());
                return commentDTO;
            }).collect(Collectors.toList());
            
            postDTO.setComments(commentDTOs);
            
            return postDTO;
        }).collect(Collectors.toList());
    }
}

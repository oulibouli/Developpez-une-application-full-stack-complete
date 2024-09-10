package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.CommentDTOCreate;
import com.openclassrooms.mddapi.dto.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.util.StringUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;

    public ResponseEntity<CommentDTO> createComment(CommentDTOCreate commentDTOCreate, int postId, UserDetails userDetails) {
        try {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));
            Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));

            if(!StringUtils.isNullOrEmpty(user.getEmail()) && !StringUtils.isNullOrEmpty(post.getTitle())) {
                Comment comment = new Comment();
                comment.setPost(post);
                comment.setUser(user);
                comment.setDate(commentDTOCreate.getDate());
                comment.setDescription(commentDTOCreate.getDescription());

                Comment savedComment = commentRepository.save(comment);

                // Convertir l'entité Comment en CommentDTO
                CommentDTO commentDTO = commentMapper.toDto(savedComment, user, post);

                return new ResponseEntity<>(commentDTO, HttpStatus.OK);
            }
            else {
                throw new EntityNotFoundException("user or post empty");
            }
        }
        catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error : " + e.getMessage());
        }
    }
}

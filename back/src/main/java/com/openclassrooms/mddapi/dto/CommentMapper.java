package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;

@Component
public class CommentMapper {
    public CommentDTO toDto(Comment comment, User user, Post post) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDescription(comment.getDescription());
        commentDTO.setDate(comment.getDate());
        commentDTO.setAuthor(user.getEmail());
        commentDTO.setPostTitle(post.getTitle());

        return commentDTO;
    }
}

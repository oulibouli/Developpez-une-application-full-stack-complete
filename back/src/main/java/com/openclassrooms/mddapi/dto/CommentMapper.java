package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;

/**
 * Mapper class for converting Comment, User, and Post entities to CommentDTO.
 */
@Component
public class CommentMapper {

    /**
     * Converts a Comment, User, and Post to a CommentDTO.
     * 
     * @param comment the Comment entity.
     * @param user the User entity.
     * @param post the Post entity.
     * @return the CommentDTO populated with data from the Comment, User, and Post.
     */
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

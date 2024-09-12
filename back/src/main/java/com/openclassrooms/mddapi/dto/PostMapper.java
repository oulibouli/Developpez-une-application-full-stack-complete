package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.Post;

/**
 * Mapper class for converting between Post entity and PostDTO.
 */
@Component
public class PostMapper {

    /**
     * Converts a Post entity to a PostDTO.
     * 
     * @param post the Post entity.
     * @return the PostDTO populated with data from the Post entity.
     */
    public PostDTO toDto(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setAuthor(post.getUser().getEmail());
        postDTO.setContent(post.getContent());
        postDTO.setDate(post.getDate());
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setTopicName(post.getTopic().getTitle());

        // Map comments from Post to CommentDTO
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
    }

    /**
     * Converts a PostDTOCreate object to a Post entity.
     * 
     * @param postDTO the DTO containing post data.
     * @return the Post entity populated with data from the DTO.
     */
    public Post toEntity(PostDTOCreate postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setDate(postDTO.getDate());
        post.setTitle(postDTO.getTitle());

        LocalDateTime currentDateTime = LocalDateTime.now();
        post.setDate(currentDateTime);

        return post;
    }
}

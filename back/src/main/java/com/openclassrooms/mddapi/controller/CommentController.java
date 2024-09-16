package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.CommentDTOCreate;
import com.openclassrooms.mddapi.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST controller for managing comments on posts.
 */
@RestController
@RequestMapping("/api/comment")
// Swagger annotation
@Tag(name = "Comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Creates a comment on a specific post.
     *
     * @param commentDTOCreate the comment details.
     * @param postId the ID of the post.
     * @param userDetails the authenticated user creating the comment.
     * @return the created comment.
     */
    @Operation(summary = "Create a new comment", description = "Creates a new comment on a specific post.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comment successfully created.", content = @Content(schema = @Schema(implementation = CommentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Post or user not found."),
        @ApiResponse(responseCode = "400", description = "Invalid input provided."),
        @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTOCreate commentDTOCreate, @PathVariable int postId, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(commentDTOCreate);
        return commentService.createComment(commentDTOCreate, postId, userDetails);
    }
}

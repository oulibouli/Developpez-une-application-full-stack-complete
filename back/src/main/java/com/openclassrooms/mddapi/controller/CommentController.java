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

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTOCreate commentDTOCreate, @PathVariable int postId, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(commentDTOCreate);
        return commentService.createComment(commentDTOCreate, postId, userDetails);
    }
}

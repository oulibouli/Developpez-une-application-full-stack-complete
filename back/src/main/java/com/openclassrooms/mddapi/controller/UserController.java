package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    // @GetMapping("/")
    // public ResponseEntity<UserDto> getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
    //     System.out.println(authorizationHeader);
    //     return userService.getUserInfo(authorizationHeader);
    // }
    
}

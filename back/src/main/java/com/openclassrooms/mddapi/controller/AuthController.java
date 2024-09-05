package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AuthDTO;
import com.openclassrooms.mddapi.dto.AuthDTOLogin;
import com.openclassrooms.mddapi.dto.AuthDTORegister;
import com.openclassrooms.mddapi.service.AuthService;

import jakarta.validation.Valid;

// Define this class as a REST controller
@RestController
@Validated
// Define the basic url for the authentication requests
@RequestMapping("/api/auth")
public class AuthController {

    // Inject the auth service
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthDTO> signUp(@Valid @RequestBody AuthDTORegister signUpRequest) {
        // Call the auth service to register a new user
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> signIn(@RequestBody AuthDTOLogin signInRequest) {
        // Call the auth service to connect a user
        return authService.signIn(signInRequest);
    }

    @GetMapping("/me")
    public ResponseEntity<AuthDTO> getUserProfile(@Valid @AuthenticationPrincipal UserDetails userDetails) {
        // Call the auth service to request the connected user
        return authService.getUserProfile(userDetails);
    }
}

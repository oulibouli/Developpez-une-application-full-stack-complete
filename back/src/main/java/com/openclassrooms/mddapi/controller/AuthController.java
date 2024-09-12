package com.openclassrooms.mddapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dto.AuthDTO;
import com.openclassrooms.mddapi.dto.AuthDTOLogin;
import com.openclassrooms.mddapi.dto.AuthDTORegister;
import com.openclassrooms.mddapi.service.AuthService;

import jakarta.validation.Valid;

/**
 * REST controller for managing user authentication (registration, login, and profile management).
 */
@RestController
@Validated
// Define the basic url for the authentication requests
@RequestMapping("/api/auth")
public class AuthController {

    // Inject the auth service
    @Autowired
    private AuthService authService;

    /**
     * Registers a new user.
     *
     * @param signUpRequest the registration details.
     * @return the registered user's authentication details.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthDTO> signUp(@Valid @RequestBody AuthDTORegister signUpRequest) {
        // Call the auth service to register a new user
        return authService.signUp(signUpRequest);
    }

    /**
     * Authenticates a user based on login credentials.
     *
     * @param signInRequest the login credentials.
     * @return the authenticated user's details and JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> signIn(@RequestBody AuthDTOLogin signInRequest) {
        // Call the auth service to connect a user
        return authService.signIn(signInRequest);
    }

    /**
     * Retrieves the profile of the authenticated user.
     *
     * @param userDetails the current authenticated user.
     * @return the authenticated user's profile.
     */
    @GetMapping("/me")
    public ResponseEntity<AuthDTO> getUserProfile(@Valid @AuthenticationPrincipal UserDetails userDetails) {
        // Call the auth service to request the connected user
        return authService.getUserProfile(userDetails);
    }

    /**
     * Updates the profile of the authenticated user.
     *
     * @param userDetails the current authenticated user.
     * @param authDTO the updated profile details.
     * @return a success message.
     */
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateUserProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AuthDTO authDTO) {
        return authService.updateUserProfile(userDetails, authDTO);
    }
}

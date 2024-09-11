package com.openclassrooms.mddapi.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openclassrooms.mddapi.dto.AuthDTO;
import com.openclassrooms.mddapi.dto.AuthDTOLogin;
import com.openclassrooms.mddapi.dto.AuthDTORegister;
import com.openclassrooms.mddapi.dto.AuthMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.util.JwtUtil;
import com.openclassrooms.mddapi.util.StringUtils;


@Service
public class AuthService {
    
    // Inject the UsersRepository bean
    @Autowired
    private UserRepository userRepository;

    // Inject the JWTUtils bean for handling JWT tokens
    @Autowired
    private JwtUtil jwtUtil;

    // Inject the AuthenticationManager bean for handling authentication
    @Autowired
    private AuthenticationManager authenticationManager;

    // Inject the AuthMapper bean for mapping between AuthDTO and Users entity
    @Autowired
    private AuthMapper authMapper;

    // Method for user signup
    public ResponseEntity<AuthDTO> signUp(AuthDTORegister authDTO) {
        AuthDTO response = new AuthDTO();
        try {
            checkRegisterInformations(authDTO);
            // Map AuthDTO to Users entity
            User newUser = authMapper.toEntity(authDTO);
            // Save the new user to the repository
            userRepository.save(newUser);

            // If the user is saved successfully, set the response details
            if(newUser != null && newUser.getId() > 0) {
                Authentication authentication = authenticateUser(newUser.getEmail(), authDTO.getPassword());
                String jwt = jwtUtil.generateToken(authentication.getName());
                response.setUser(newUser);
                response.setToken(jwt);
            }
        }
        catch (IllegalArgumentException e) {
             // Return bad request if there is an illegal argument
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }catch (IllegalStateException e) {
            // Return conflict if the user already exists
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Handle any other exceptions by setting the error details in the response
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Method for user signin
    public ResponseEntity<AuthDTO> signIn(AuthDTOLogin authDTO) {
        AuthDTO response = new AuthDTO();
        try {
            checkLoginInformations(authDTO);

            String email = "";
            if(authDTO.getIdentifier().contains("@")) {
                email = authDTO.getIdentifier();
            }
            else if(StringUtils.isNullOrEmpty(authDTO.getUsername())) {
                User checkUser = userRepository.findByUsername(authDTO.getIdentifier()).orElseThrow();
                email = checkUser.getEmail();
            }
            

            // Authenticate the user with the provided credentials
            Authentication authentication = authenticateUser(email, authDTO.getPassword());
            // Generate a JWT token for the authenticated user
            String jwt = jwtUtil.generateToken(authentication.getName());
            // Set the response details
            response.setToken(jwt);
            response.setMessage("Successfully signed in");
        } catch (IllegalArgumentException e) {
            // Return bad request if there is an illegal argument
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       } catch (Exception e) {
           // Handle any other exceptions by setting the error details in the response
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }

       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Method to get the user profile based on user details
    public ResponseEntity<AuthDTO> getUserProfile(UserDetails userDetails) {
        AuthDTO response = new AuthDTO();
        try {
            response = authMapper.toDTO(userDetails);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            // Handle any other exceptions by setting the error details in the response
            response.setError(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, Object>> updateUserProfile(UserDetails userDetails, AuthDTO authDTO) {   
        try {
            User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + authDTO.getEmail()));
    
            User updatedUser = authMapper.toUpdatedEntity(user, authDTO);
            
            userRepository.save(updatedUser);
    
            return ResponseEntity.ok(Collections.singletonMap("message", "User updated successfully!"));
    
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating user: " + e.getMessage());
        }
    }

    // Method to authenticate a user with the authentication manager.
    private Authentication authenticateUser(String identifier, String password) {
        try {
            // Tente d'authentifier l'utilisateur avec les identifiants fournis (email ou username).
            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(identifier, password)
            );
        } catch (BadCredentialsException e) {
            // Si l'authentification échoue à cause de mauvaises informations d'identification.
            throw new BadCredentialsException("Invalid email/username or password.");
        }
    }

    private void checkRegisterInformations(AuthDTORegister authDTORegister) {
        if(StringUtils.isNullOrEmpty(authDTORegister.getEmail()) || 
            StringUtils.isNullOrEmpty(authDTORegister.getUsername()) || 
            StringUtils.isNullOrEmpty(authDTORegister.getPassword())) {
            throw new IllegalArgumentException("Name, password, or email cannot be empty.");
        }
        if(userRepository.existsByEmail(authDTORegister.getEmail())) {
            throw new IllegalStateException("A user with this email already exists.");
        }
        if(userRepository.existsByUsername(authDTORegister.getUsername())) {
            throw new IllegalStateException("A user with this username already exists.");
        }
    }
    
    private void checkLoginInformations(AuthDTOLogin authDTOLogin) {
        if(StringUtils.isNullOrEmpty(authDTOLogin.getPassword())){
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if(StringUtils.isNullOrEmpty(authDTOLogin.getIdentifier())){
            throw new IllegalArgumentException("Email or username cannot be empty.");
        }
    }
}

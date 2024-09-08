package com.openclassrooms.mddapi.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

@Component // Annotation for Spring-managed component.
public class AuthMapper {

    @Autowired // Inject dependency of the PasswordEncoder bean
    private PasswordEncoder passwordEncoder;
    
    @Autowired // Inject dependency of the UsersRepository bean
    private UserRepository userRepository;

    @Value("${default.role}") // Inject the default role for a new user from the properties file
    private String defaultRole;

    // Converts an AuthDTO object to a Users entity.
    public User toEntity(AuthDTORegister authDTO) {
        User user = new User();
        user.setEmail(authDTO.getEmail()); // Setting the email from authDTO to Users entity.
        user.setUsername(authDTO.getUsername()); // Setting the name from authDTO to Users entity.
        user.setPassword(passwordEncoder.encode(authDTO.getPassword())); // Encoding the password and setting it.
        user.setRole(defaultRole); // Setting a default role for the user.

        return user; // Returning the created Users entity.
    }

    // Converts a UserDetails object to an AuthDTO object.
    public AuthDTO toDTO(UserDetails userDetails) {
        AuthDTO authDTO = new AuthDTO();
        try {
            // Retrieving the Users entity by email (username) from the repository.
            User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
            // Setting the properties of authDTO from the retrieved Users entity.
            authDTO.setId(user.getId());
            authDTO.setUsername(user.getUsername());
            authDTO.setEmail(user.getEmail());
            authDTO.setRole(user.getRole());
        } catch (Exception e) {
            authDTO.setError(e.getMessage()); // Setting the error message from the exception.
        }
        return authDTO; // Returning the created AuthDTO object.
    }
}

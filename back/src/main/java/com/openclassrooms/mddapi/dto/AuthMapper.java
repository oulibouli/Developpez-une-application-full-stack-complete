package com.openclassrooms.mddapi.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.util.StringUtils;

/**
 * Mapper class for converting between DTOs and user entities.
 * Handles converting from AuthDTO to User and vice versa.
 */
@Component // Annotation for Spring-managed component.
public class AuthMapper {

    @Autowired // Inject dependency of the PasswordEncoder bean
    private PasswordEncoder passwordEncoder;
    
    @Autowired // Inject dependency of the UsersRepository bean
    private UserRepository userRepository;

    @Value("${default.role}") // Inject the default role for a new user from the properties file
    private String defaultRole;

    /**
     * Converts AuthDTORegister to a User entity.
     * 
     * @param authDTO the registration DTO containing user data.
     * @return the User entity populated with data from the DTO.
     */
    public User toEntity(AuthDTORegister authDTO) {
        User user = new User();
        user.setEmail(authDTO.getEmail()); // Setting the email from authDTO to Users entity.
        user.setUsername(authDTO.getUsername()); // Setting the name from authDTO to Users entity.
        user.setPassword(passwordEncoder.encode(authDTO.getPassword())); // Encoding the password and setting it.
        user.setRole(defaultRole); // Setting a default role for the user.

        return user; // Returning the created Users entity.
    }
    
    /**
     * Updates an existing User entity with new data from AuthDTO.
     * 
     * @param user the existing User entity.
     * @param authDTO the DTO containing updated user data.
     * @return the updated User entity.
     */
    public User toUpdatedEntity(User user, AuthDTO authDTO) {
        user.setEmail(authDTO.getEmail()); // Setting the email from authDTO to Users entity.
        user.setUsername(authDTO.getUsername()); // Setting the name from authDTO to Users entity.
        
        if(!StringUtils.isNullOrEmpty(authDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(authDTO.getPassword())); // Encoding the password and setting it.
        }
        return user; // Returning the created Users entity.
    }

    /**
     * Converts UserDetails to an AuthDTO object.
     * 
     * @param userDetails the UserDetails object representing an authenticated user.
     * @return the AuthDTO populated with user data.
     */
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

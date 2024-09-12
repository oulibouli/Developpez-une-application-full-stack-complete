package com.openclassrooms.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

/**
 * Service class for managing user details and authentication.
 * Implements Spring Security's UserDetailsService interface.
 */
@Service
public class UserService implements UserDetailsService {

    // Inject the user repo
    @Autowired
    private UserRepository userRepository;

    // Inject the user mapper to convert between DTO and entity
    @Autowired
    UserMapper userMapper;
    
    /**
     * Load a user by their username or email for authentication purposes.
     * 
     * @param identifier The username or email of the user.
     * @return UserDetails containing the user's information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Get the user by email from the repo / Return an exception if not found
        Optional<User> userOpt = userRepository.findByEmail(identifier);
        
        // Si l'utilisateur n'a pas été trouvé par email, on tente par username
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByUsername(identifier);
        }

        // Si l'utilisateur n'est pas trouvé dans les deux cas, on lève une exception
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + identifier));
    }
}


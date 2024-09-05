package com.openclassrooms.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.dto.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;



@Service
public class UserService implements UserDetailsService {

    // Inject the user repo
    @Autowired
    private UserRepository userRepository;

    // Inject the user mapper to convert between DTO and entity
    @Autowired
    UserMapper userMapper;
    
    // Request a user by username (email)
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

    // Get a user by id
    public ResponseEntity<UserDTO> getUserById(int id) {
        // Get the user by id from the repo
        Optional<User> user = userRepository.findById(id);
        ResponseEntity<UserDTO> response;

        // If user exist, map it to DTO and return a HTTP code 200 : success
        if (user.isPresent()) {
            UserDTO userDTO = userMapper.toDTO(user.get());
            response = new ResponseEntity<>(userDTO, HttpStatus.OK);
        } else {
            // If user not found, return a HTTP code 404 : Not Found
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}


package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;

// Spring-managed component.
@Component
public class UserMapper {

    // Converts a Users entity to a UserDTO object.
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        
        // Mapping fields from Users entity to UserDTO.
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        
        return userDTO; // Returning the created UserDTO.
    }
}
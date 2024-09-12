package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;

/**
 * Mapper class for converting between User entity and UserDTO.
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity to a UserDTO.
     * 
     * @param user the User entity.
     * @return the UserDTO populated with data from the User entity.
     */
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        
        // Mapping fields from Users entity to UserDTO.
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        
        return userDTO; // Returning the created UserDTO.
    }
}
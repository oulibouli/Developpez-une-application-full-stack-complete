package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * DTO for representing a User.
 * Contains user details such as email and username.
 */
@Data // Generates automatically the getters and setters
public class UserDTO {

    private int id;
    @Email(message = "Email should be valid")
    private String email;
    private String username;
}
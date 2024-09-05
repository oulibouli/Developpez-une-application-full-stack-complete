package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data // Generates automatically the getters and setters
public class UserDTO {

    private int id;
    @Email(message = "Email should be valid")
    private String email;
    private String username;
}
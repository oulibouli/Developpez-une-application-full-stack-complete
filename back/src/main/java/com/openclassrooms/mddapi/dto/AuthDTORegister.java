package com.openclassrooms.mddapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for user registration.
 * Contains user details such as username, email, and password.
 */
@Data // Generates automatically the getters and setters
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class AuthDTORegister {

    @NotNull(message = "Cannot be null")
    private String username;
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Cannot be null")
    private String password;
}

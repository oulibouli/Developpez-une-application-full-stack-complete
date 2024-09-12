package com.openclassrooms.mddapi.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for login-related information.
 * Contains login credentials such as email, password, username, or identifier.
 */
@Data // Generates automatically the getters and setters
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class AuthDTOLogin {

    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Cannot be null")
    private String password;
    private String username;
    private String identifier;
}

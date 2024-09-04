package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown= true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Don't include null values in the json response
public class UserDto {
    private Long id;
    @NotNull(message = "Cannot be null")
    @Email
    private String email;
    @NotNull(message = "Cannot be null")
    private String password;
    @NotNull(message = "Cannot be null")
    private String username;

    private String token;
}

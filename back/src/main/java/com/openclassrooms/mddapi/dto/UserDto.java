package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long id;
    @NotNull(message = "Cannot be null")
    @Email
    private String email;
    @NotNull(message = "Cannot be null")
    private String password;
    @NotNull(message = "Cannot be null")
    private String username;
}

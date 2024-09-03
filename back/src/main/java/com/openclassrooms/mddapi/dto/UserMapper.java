package com.openclassrooms.mddapi.dto;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .username(user.getUsername())
            .build();
    }
    
    public User toEntity(UserDto userDto) {
        return User.builder()
            .id(userDto.getId())
            .email(userDto.getEmail())
            .password(userDto.getPassword())
            .username(userDto.getUsername())
            .build();
    }
}

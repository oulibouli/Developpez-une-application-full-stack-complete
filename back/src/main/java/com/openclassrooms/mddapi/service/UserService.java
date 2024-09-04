package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.dto.UserMapper;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.util.JWTUtils;
import com.openclassrooms.mddapi.util.StringUtils;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    

    public ResponseEntity<UserDto> getUserInfo(String authorizationHeader) {
        String email = verifyUserValidityFromToken(authorizationHeader);

        return userRepository.findByEmail(email)
            .map(user -> ResponseEntity.ok(userMapper.toDto(user)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<UserDto> signUp(UserDto userDto) {
        UserDto response = new UserDto();
        try {
            checkRegisterInformations(userDto);
            User user = userMapper.toEntity(userDto);

            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);

            if(user.getId() > 0) {
                response = userMapper.toDto(user);
                String jwt = jwtUtils.generateToken(user.getEmail());
                response.setToken(jwt);
            }
        }
        catch (IllegalArgumentException e) {
            // Return bad request if there is an illegal argument
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
       }catch (IllegalStateException e) {
           // Return conflict if the user already exists
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.CONFLICT);
       } catch (Exception e) {
           // Handle any other exceptions by setting the error details in the response
           response.setError(e.getMessage());
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void checkRegisterInformations(UserDto userDto) {
        if(StringUtils.isNullOrEmpty(userDto.getEmail()) || 
            StringUtils.isNullOrEmpty(userDto.getUsername()) || 
            StringUtils.isNullOrEmpty(userDto.getPassword())) {
            throw new IllegalArgumentException("Name, password, or email cannot be empty.");
        }
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalStateException("A user with this email already exists.");
        }
    }


    private String verifyUserValidityFromToken(String authorizationHeader) {
        String jwtToken = authorizationHeader.substring(7);
        String username = jwtUtils.extractUsername(jwtToken);

        return username;
    }
}

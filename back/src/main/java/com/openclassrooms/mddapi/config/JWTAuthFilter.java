package com.openclassrooms.mddapi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.mddapi.exceptions.AuthenticationFailureException;
import com.openclassrooms.mddapi.exceptions.InvalidJwtTokenException;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.util.JwtUtil;
import com.openclassrooms.mddapi.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Annotation : Spring component
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    // Spring dependency injection for the jwt tools
    @Autowired
    private JwtUtil jwtUtil;

    // Spring dependency injection for the users service
    @Autowired
    private UserService usersService;

    // Main method requested for each HTTP request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Get the authorization header for the request
        final String authHeader = request.getHeader("Authorization");

        // If the request is about images, don't filter
        if (request.getRequestURI().startsWith("/images/")) {
            filterChain.doFilter(request, response); // Bypass the request and the response to the next filter
            return;
        }

        // Check if the authorization header is missing or malformed
        if(StringUtils.isNotValidAuthorizationHeader(authHeader)){
            filterChain.doFilter(request, response); // Bypass the request and the response to the next filter
            return;
        }
        // Extract the JWT token from the authorization header
        String jwtToken = authHeader.substring(7);
        try {
            processJwtToken(jwtToken, request);
        } catch (UsernameNotFoundException e) {
            throw new AuthenticationFailureException("JWT token processing failed");
        }

        // Pass the request and the response to the next filter
        filterChain.doFilter(request, response);
    }

    private void processJwtToken(String jwtToken, HttpServletRequest request) {
        // Extract the username from the JWT token
        String userEmail = jwtUtil.extractUsername(jwtToken);

        // If the username is not null and the user is not authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user details
            UserDetails userDetails = usersService.loadUserByUsername(userEmail);

            // Check if the JWT token is valid
            if (jwtUtil.isTokenValid(jwtToken, userDetails)) {
                // Create an authentication token and pass it to the security
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(token);
            } else {
                throw new InvalidJwtTokenException("Invalid JWT token");
            }
        } else {
            throw new AuthenticationFailureException("Username is null or user is already authenticated");
        }
    }
    
}

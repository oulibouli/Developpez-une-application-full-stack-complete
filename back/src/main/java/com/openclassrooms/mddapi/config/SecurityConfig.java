package com.openclassrooms.mddapi.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.openclassrooms.mddapi.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Security configuration class for the application.
 * This class configures HTTP security, JWT authentication, and CORS settings.
 */

@Configuration // Spring configuration class
@EnableWebSecurity // Enable the web security in the app
public class SecurityConfig {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JWTAuthFilter jwtAuthFilter;
    
    @Value("${chatop.security.cors.origins}") // Inject the cors origins from the properties file
    private String corsOrigins;


    /**
     * Configures the security filter chain, including JWT, CORS, and session management.
     *
     * @param http the HttpSecurity object used to configure the security aspects of the app.
     * @return the security filter chain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Disable the CSRF protection
        .cors(Customizer.withDefaults()) // Apply the default CORS configuration
        .formLogin(login -> login.disable()) // Disable the login form
        .httpBasic(basic -> basic.disable()) // Disable the HTTP basic authentication
        .authorizeHttpRequests(request -> request
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // Define the authorization access for the URLs
            .requestMatchers(
                "/api/auth/login",
                "/api/auth/register",
                "/images/**",
                "/v3/api-docs/**",
                "/swagger-ui/**"
            ).permitAll()
            .requestMatchers(
                "/api/topic/**",
                "/api/post/**",
                "/api/comment/**",
                "/api/subscription/**"
            ).hasAnyRole("USER", "ADMIN")
            // Any request has to be authenticated
            .anyRequest().authenticated()
        )
        // Sessions stateless -> No user session keep on server-side
        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // Define the auth provider
        .authenticationProvider(authenticationProvider())
        // Add the JWT filter before the default auth filter
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Configures CORS to allow requests from specified origins.
     *
     * @return a CorsFilter with the appropriate configuration.
     */
    @Bean
    CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200")); // Autoriser uniquement ce domaine
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source) {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
         
                super.doFilterInternal(request, response, filterChain);
            }
        };
    }
    
    /**
     * Configures the authentication provider to use the user service and password encoder.
     *
     * @return the authentication provider.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // Define the user service and the password encoder
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    /**
     * Provides a BCrypt password encoder.
     *
     * @return the password encoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        // Return the BCrypt password encoder
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the authentication manager from the authentication configuration.
     *
     * @param authenticationConfiguration the authentication configuration.
     * @return the authentication manager.
     * @throws Exception if an error occurs during authentication manager creation.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Return the auth manager from the config
        return authenticationConfiguration.getAuthenticationManager();
    }
}

package com.openclassrooms.mddapi.util;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 * This includes generating, validating, and extracting information from tokens.
 */
@Component
public class JwtUtil {

    // Inject the secret key from the properties file
    @Value("${jwt.secret}")
    private String secretString;
    
    // Token validity in ms
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    // Decode the secret key and prepare it to sign the JWT
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * The `generateToken` function creates a JWT token with specified claims and a signature
     * algorithm.
     * 
     * @param userName The `userName` parameter is the username for which the token is being generated.
     * It is used as the subject of the JWT (JSON Web Token) to identify the user associated with the
     * token.
     * @return The method `generateToken` returns a JSON Web Token (JWT) generated with the specified
     * claims including the subject (user name), created date, expiration date, and algorithm
     * signature.
     */
    public String generateToken(String userName) {
        // Build the JWT with the claims : Subject, Created date, expiration date and the algorythm signature
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * The function `extractUsername` extracts the username from a token using the `getSubject` method
     * of the `Claims` class.
     * 
     * @param token I see that you have a method `extractUsername` that takes a token as a parameter.
     * The method extracts the username from the token by calling the `extractClaim` method with the
     * `Claims::getSubject` function reference.
     * @return The method `extractUsername` is returning the subject claim extracted from the token
     * using the `Claims::getSubject` method.
     */
    public String extractUsername(String token) {
        System.out.println("Extracting Username from Token: " + token);
        System.out.println(extractClaim(token, Claims::getSubject));
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * The function `extractClaim` takes a token and a function that resolves claims, then extracts all
     * claims from the token and applies the resolver function to return the result.
     * 
     * @param token Token is a string that represents a JSON Web Token (JWT) containing claims.
     * @param claimsResolver The `claimsResolver` parameter in the `extractClaim` method is a
     * `Function` functional interface that takes a `Claims` object as input and returns a result of
     * type `T`. It is used to extract specific information or data from the `Claims` object obtained
     * from the token. You can
     * @return The method `extractClaim` returns the result of applying the `claimsResolver` function
     * to the `claims` extracted from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all the claims from the JWT
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey()) // Définit la clé de signature pour la validation.
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            // Lance une exception personnalisée en cas d'erreur de validation du JWT.
            throw new JwtException("JWT validation error: " + e.getMessage());
        }
    }

    /**
     * The function `isTokenValid` checks if a token is valid by comparing the extracted username with
     * the username in the user details and verifying that the token is not expired.
     * 
     * @param token A token is a string that represents the authentication credentials of a user. It is
     * typically generated by a server and provided to a client for authentication purposes.
     * @param userDetails UserDetails is an object that contains details about a user, such as their
     * username, password, and other relevant information. In this context, it is used to compare the
     * username extracted from a token with the username stored in the UserDetails object to determine
     * if the token is valid for that user.
     * @return The method is returning a boolean value, which indicates whether the token is valid for
     * the given user details.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * The function `isTokenExpired` checks if a token has expired by comparing its expiration claim
     * with the current date.
     * 
     * @param token A JWT token that contains information about the user and their authentication
     * status.
     * @return The method isTokenExpired is returning a boolean value, which indicates whether the
     * token has expired or not.
     */
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

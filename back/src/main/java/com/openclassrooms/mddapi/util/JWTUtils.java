package com.openclassrooms.mddapi.util;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtils {

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

    // Generate a JWT Token for the user
    public String generateToken(String userName) {
        // Build the JWT with the claims : Subject, Created date, expiration date and the algorythm signature
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey())
                .compact();
    }

    // Extract the user's username from the JWT token.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract a specific claim from the JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all the claims from the JWT
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                .verifyWith((SecretKey) getSignKey()) // Utilise la clé pour vérifier la signature du token.
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException e) {
            // Lance une exception personnalisée en cas d'erreur de validation du JWT.
            throw new JwtException("JWT validation error: " + e.getMessage());
        }
    }

    // Check if the JWT Token is valid by comparing with the extracted username and checking the expiration
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));

    }
    // Check it the JWT token is expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}

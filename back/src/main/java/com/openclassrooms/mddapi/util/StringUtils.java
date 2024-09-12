package com.openclassrooms.mddapi.util;

/**
 * Utility class for handling common string operations.
 */
public class StringUtils {

    /**
     * Check if a string is null or empty.
     * 
     * @param str The string to check.
     * @return true if the string is null or empty.
     */
    public static boolean isNullOrEmpty (String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Validate if an authorization header is properly formatted.
     * 
     * @param authHeader The authorization header string.
     * @return true if the header is invalid.
     */
    public static boolean isNotValidAuthorizationHeader(String authHeader) {
        return authHeader == null || authHeader.isBlank() || !authHeader.startsWith("Bearer ");
    }
}

package com.aifraud.AiFraud.security;

import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class JwtUtil {
    
    private final String SECRET_KEY = "ai_fraud_detection_secret_key_2024";
    
    public String generateToken(String username) {
        String token = username + "|" + System.currentTimeMillis();
        return Base64.getEncoder().encodeToString(token.getBytes());
    }
    
    public String extractUsername(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return decoded.split("\\|")[0];
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean validateToken(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] parts = decoded.split("\\|");
            if (parts.length == 2) {
                long timestamp = Long.parseLong(parts[1]);
                long currentTime = System.currentTimeMillis();
                return (currentTime - timestamp) < 86400000;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
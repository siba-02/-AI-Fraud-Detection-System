package com.aifraud.AiFraud.controller;

import com.aifraud.AiFraud.model.User;
import com.aifraud.AiFraud.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        String result = authService.register(user);
        Map<String, String> response = new HashMap<>();

        if ("REGISTER_SUCCESS".equals(result)) {
            response.put("status", "success");
            response.put("message", "Registration successful! Please login.");
            return ResponseEntity.ok(response);
        } else if ("EMAIL_EXISTS".equals(result)) {
            response.put("status", "error");
            response.put("message", "Email already exists!");
            return ResponseEntity.badRequest().body(response);
        } else {
            response.put("status", "error");
            response.put("message", "Username already exists!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        String result = authService.login(user.getUsername(), user.getPassword());
        Map<String, String> response = new HashMap<>();

        if ("LOGIN_SUCCESS".equals(result)) {
            response.put("status", "success");
            response.put("message", "Login successful!");
            response.put("username", user.getUsername());
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Login failed! Invalid credentials.");
            return ResponseEntity.status(401).body(response);
        }
    }
    
    @GetMapping("/check/{username}")
    public ResponseEntity<Boolean> checkUsername(@PathVariable String username) {
        return ResponseEntity.ok(authService.usernameExists(username));
    }
}
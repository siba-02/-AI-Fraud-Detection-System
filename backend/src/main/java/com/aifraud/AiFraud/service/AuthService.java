package com.aifraud.AiFraud.service;

import com.aifraud.AiFraud.model.User;
import com.aifraud.AiFraud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ ADD THIS

    // ✅ REGISTER
    public String register(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            return "USERNAME_EXISTS";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return "EMAIL_EXISTS";
        }

        // ✅ IMPORTANT: ENCODE PASSWORD
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "REGISTER_SUCCESS";
    }

    // ✅ LOGIN
    public String login(String username, String password) {

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {

            User user = userOpt.get();

            // ✅ FIX (IMPORTANT)
            if (passwordEncoder.matches(password, user.getPassword())) {

                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);

                return "LOGIN_SUCCESS";
            }
        }

        return "LOGIN_FAILED";
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public long getTotalUsers() {
        return userRepository.countTotalUsers();
    }
}
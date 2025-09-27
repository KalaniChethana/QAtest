package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User signup(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (userRepo.findByUsername(username) != null) {
            return null; // username already exists
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepo.save(user);
    }

    private void validateSignup(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (password.length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }
    }

    public User login(String username, String password) {
        User user = userRepo.findByUsername(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }
}

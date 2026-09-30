package com.driveease.user.service;

import com.driveease.user.model.User;
import com.driveease.user.util.JwtUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<String, User> userStorage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final JwtUtils jwtUtils;

    public UserService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostConstruct
    public void initDefaultUsers() {
        registerUser("john_doe", "john@example.com", "password123", "ROLE_CUSTOMER");
        registerUser("admin", "admin@driveease.com", "admin123", "ROLE_ADMIN");
    }

    public User registerUser(String username, String email, String password, String role) {
        if (userStorage.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        Long newId = idGenerator.getAndIncrement();
        User user = new User(newId, username, email, password, role);
        userStorage.put(username, user);
        return user;
    }

    public String login(String username, String password) {
        User user = userStorage.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return jwtUtils.generateToken(user.getUsername(), user.getRole(), user.getUserId());
    }

    public User getUserByUsername(String username) {
        return userStorage.get(username);
    }
}

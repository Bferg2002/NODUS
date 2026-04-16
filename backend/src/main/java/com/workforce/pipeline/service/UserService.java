package com.workforce.pipeline.service;

import com.workforce.pipeline.model.User;
import com.workforce.pipeline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // CREATE (Register User)
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // READ (Get all users)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ (Get user by ID)
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }
}
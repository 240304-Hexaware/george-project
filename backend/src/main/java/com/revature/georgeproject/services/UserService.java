package com.revature.georgeproject.services;

import com.revature.georgeproject.models.User;
import com.revature.georgeproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User register(String username, String password) {
        User u = new User(username, password);
        userRepository.save(u);
        return u;
    }

    public User login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

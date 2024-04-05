package com.revature.georgeproject.controllers;

import com.mongodb.MongoWriteException;
import com.revature.georgeproject.models.User;
import com.revature.georgeproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {this.userService = userService;}

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User u = userService.login(username,password);
        if (u == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(u);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<User> register(@RequestParam("username") String username, @RequestParam("password") String password) {
        return ResponseEntity.ok(userService.register(username,password));
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ExceptionHandler(MongoWriteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userAlreadyExists(MongoWriteException e) {
        return "User already exists";
    }
}

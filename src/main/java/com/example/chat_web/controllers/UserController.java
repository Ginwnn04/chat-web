package com.example.chat_web.controllers;

import com.example.chat_web.entities.Users;
import com.example.chat_web.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/users")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Users user) {

        boolean success = userService.login(user.getUsername(), user.getPassword()) != null ? true : false;
        return success;
    }

}

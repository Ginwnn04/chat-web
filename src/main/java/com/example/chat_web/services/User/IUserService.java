package com.example.chat_web.services.User;

import com.example.chat_web.entities.Users;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Users> getAllUsers();

    Users login(String username, String password);
}

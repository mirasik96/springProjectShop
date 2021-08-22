package com.example.demo.services;

import com.example.demo.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getUser(String email);
    User addUser(User user);
    User updateUser(User user);
}

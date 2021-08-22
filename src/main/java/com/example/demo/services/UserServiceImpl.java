package com.example.demo.services;

import com.example.demo.entities.Role;
import com.example.demo.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("User Not Found");
        }
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User addUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser == null){
            Role role = roleRepository.findByRole("ROLE_USER");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(role);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}

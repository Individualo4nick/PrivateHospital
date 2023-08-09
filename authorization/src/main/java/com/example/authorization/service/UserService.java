package com.example.authorization.service;

import com.example.authorization.config.SecurityConfig;
import com.example.authorization.domain.entity.Enums.Role;
import com.example.authorization.domain.entity.User;
import com.example.authorization.dtos.UserRegisterDto;
import com.example.authorization.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public Optional<User> getByLogin(String login){
        return userRepo.getUserByLogin(login);
    }
    public void saveUser(UserRegisterDto userReg){
        User user = new User();
        user.setLogin(userReg.login).setPassword(SecurityConfig.passwordEncoder().encode(userReg.password)).setRole(userReg.role);
        userRepo.save(user);
    }
}

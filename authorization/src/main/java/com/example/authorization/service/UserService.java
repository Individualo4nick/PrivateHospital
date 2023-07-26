package com.example.authorization.service;

import com.example.authorization.domain.entity.User;
import com.example.authorization.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    Optional<User> getByEmail(String email){
        return userRepo.getUserByEmail(email);
    }
}

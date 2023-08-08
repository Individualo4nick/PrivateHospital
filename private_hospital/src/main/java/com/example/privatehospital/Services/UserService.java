package com.example.privatehospital.Services;

import com.example.privatehospital.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{
    void saveUserById(Long id);
    User getUserInfo(Long id);
    void saveUser(User user);
}

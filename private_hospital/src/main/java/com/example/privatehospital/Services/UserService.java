package com.example.privatehospital.Services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService{
    void saveUserById(Long id);
}

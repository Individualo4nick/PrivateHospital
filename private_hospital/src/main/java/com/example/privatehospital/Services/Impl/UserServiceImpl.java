package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Repositories.UserRepository;
import com.example.privatehospital.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    public void saveUserById(Long id){
        User user = new User();
        user.setId(id);
        userRepository.save(user);
    }

    public User getUserInfo(Long id) {
        return userRepository.getUserById(id);
    }
}

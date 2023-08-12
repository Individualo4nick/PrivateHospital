package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Repositories.UserRepository;
import com.example.privatehospital.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Value("${files.path}")
    private String imagePath;
    public void saveUserById(Long id){
        User user = new User();
        user.setId(id);
        userRepository.save(user);
    }

    public User getUserInfo(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public File getUserImage(String userName) {
        File f = new File(imagePath + "/images/user");
        try {
            File[] matchingFiles = f.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith(userName);
                }
            });
            return matchingFiles[0];
        } catch (Exception e) {
            File[] matchingFiles = f.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("anon");
                }
            });
            return matchingFiles[0];
        }
    }
}

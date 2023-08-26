package com.example.privatehospital.Services;

import com.example.privatehospital.Entities.Comment;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.File;
import java.text.ParseException;
import java.util.List;

public interface UserService{
    void saveUserById(Long id);
    User getUserInfo(Long id);
    void saveUser(User user);
    File getUserImage(String userTitle);
    List<Record> getFutureRecords(List<Record> records) throws ParseException;
    void addComment(Comment comment);
}

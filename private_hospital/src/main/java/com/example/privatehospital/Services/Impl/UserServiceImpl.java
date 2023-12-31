package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.Entities.Comment;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Repositories.CommentRepository;
import com.example.privatehospital.Repositories.RecordRepository;
import com.example.privatehospital.Repositories.UserRepository;
import com.example.privatehospital.Services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final RecordRepository recordRepository;
    public UserServiceImpl(UserRepository userRepository, CommentRepository commentRepository, RecordRepository recordRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.recordRepository = recordRepository;
    }

    @Value("${files.path}")
    private String imagePath;
    public void saveUserById(Long id){
        User user = new User();
        user.setId(id);
        userRepository.save(user);
    }

    public User getUserInfo(Long id) {
        return userRepository.getUserByIdUser(id);
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
    @Override
    public void addComment(Comment comment){
        commentRepository.save(comment);
    }
    @Override
    public Record getRecordByClientRecordId(Long clientRecordId){
        return recordRepository.getRecordById(clientRecordId);
    }
    @Override
    public void updateRecord(RecordDto recordDto){
        recordRepository.updateRecordsField(recordDto.id, recordDto.description, recordDto.service, recordDto.price);
    }
}

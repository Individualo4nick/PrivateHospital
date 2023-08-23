package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.Entities.ClientRecord;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Repositories.UserRepository;
import com.example.privatehospital.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Record> getFutureRecords(List<Record> records) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance().getTime();
        ArrayList<Record> futureDates = new ArrayList<>();
        for (Record record : records) {
            Date currentDate = dateFormat.parse(record.getVisitDate());
            if (currentDate.after(today)) {
                futureDates.add(record);
            }
        }
        return futureDates;
    }
}

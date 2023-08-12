package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Repositories.StaffRepository;
import com.example.privatehospital.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;
    @Value("${files.path}")
    private String imagePath;
    public void saveStaffById(Long id){
        Staff staff = new Staff();
        staff.setId(id);
        staffRepository.save(staff);
    }

    public Staff getStaffInfo(Long id) {
        return staffRepository.getStaffById(id);
    }

    @Override
    public void saveStaff(Staff staff) {
        staffRepository.save(staff);
    }
    @Override
    public File getStaffImage(String userName) {
        File f = new File(imagePath + "/images/staff");
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

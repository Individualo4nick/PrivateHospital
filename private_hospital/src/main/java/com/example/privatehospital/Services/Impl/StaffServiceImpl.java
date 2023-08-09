package com.example.privatehospital.Services.Impl;

import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Repositories.StaffRepository;
import com.example.privatehospital.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;
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
}

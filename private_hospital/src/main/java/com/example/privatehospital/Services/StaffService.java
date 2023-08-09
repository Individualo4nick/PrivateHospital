package com.example.privatehospital.Services;

import com.example.privatehospital.Entities.Staff;

public interface StaffService {
    void saveStaffById(Long id);
    Staff getStaffInfo(Long id);
    void saveStaff(Staff staff);
}

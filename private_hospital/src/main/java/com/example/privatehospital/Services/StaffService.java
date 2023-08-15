package com.example.privatehospital.Services;

import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.StaffFilter;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;

public interface StaffService {
    void saveStaffById(Long id);
    Staff getStaffInfo(Long id);
    void saveStaff(Staff staff);
    File getStaffImage(String staffTitle);
    Page<Staff> getAllStaff(StaffFilter filter, Pageable pageable);
}

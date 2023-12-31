package com.example.privatehospital.Services;

import com.example.privatehospital.DTOs.StaffDto;
import com.example.privatehospital.Entities.Comment;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.StaffFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.text.ParseException;
import java.util.List;

public interface StaffService {
    void saveStaffById(Long id);
    Staff getStaffInfo(Long id);
    Staff saveStaff(Staff staff);
    File getStaffImage(String staffTitle);
    Page<Staff> getAllStaff(StaffFilter filter, Pageable pageable);
    List<Record> getFutureRecords(List<Record> records) throws ParseException;
    List<Comment> getAllComment(Long staffId);
    void deleteStaffById(Long id);
    void deleteCommentById(Long id);
}

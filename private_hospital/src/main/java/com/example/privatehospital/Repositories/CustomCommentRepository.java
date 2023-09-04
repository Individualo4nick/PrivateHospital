package com.example.privatehospital.Repositories;

import com.example.privatehospital.Entities.Comment;

import java.util.List;

public interface CustomCommentRepository {
    List findAllByStaffId(Long staffId);
}

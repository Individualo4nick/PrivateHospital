package com.example.privatehospital.Controllers;

import com.example.privatehospital.Services.StaffService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/server/admin")
public class AdminController {
    @Value("${files.path}")
    private String imagePath;
    private final StaffService staffService;

    public AdminController(StaffService staffService) {
        this.staffService = staffService;
    }

    @DeleteMapping("/delete_staff/{id}")
    public Integer deleteStaff(@PathVariable Long id){
        staffService.deleteStaffById(id);
        return Response.SC_OK;
    }
    @DeleteMapping("/delete_comment/{id}")
    public Integer deleteComment(@PathVariable Long id){
        staffService.deleteCommentById(id);
        return Response.SC_OK;
    }
}


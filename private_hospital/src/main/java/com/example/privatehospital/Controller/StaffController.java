package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Services.StaffService;
import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class StaffController {
    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @PostMapping(value = "/staff/registration")
    public Integer saveStaff(@RequestBody IdDto idDto){
        staffService.saveStaffById(idDto.id);
        return Response.SC_OK;
    }

    @GetMapping(value = "/staff/{id}")
    public Staff getStaffInfo(@PathVariable Long id){
        return staffService.getStaffInfo(id);
    }
    @PutMapping(value = "/staff")
    public Integer updateStaff(@RequestBody Staff staff){
        staffService.saveStaff(staff);
        return Response.SC_OK;
    }
}
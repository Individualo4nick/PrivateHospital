package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.DTOs.StaffFilterDto;
import com.example.privatehospital.DTOs.PageResponse;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Services.StaffService;
import com.example.privatehospital.StaffFilter;
import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    @GetMapping("/staff_image/{name}")
    public byte[] getStaffImage(@PathVariable String name) throws IOException {
        File serverFile = staffService.getStaffImage(name);
        return Files.readAllBytes(serverFile.toPath());
    }

    @PostMapping("/staff/all/filter")
    public PageResponse getAllStaff(@RequestBody StaffFilterDto staffFilterDto) {
        System.out.println(staffFilterDto.position);
        StaffFilter filter = new StaffFilter(staffFilterDto.position, staffFilterDto.department);
        Page<Staff> page;
        if (staffFilterDto.page != null && staffFilterDto.size != null)
            page = staffService.getAllStaff(filter, PageRequest.of(staffFilterDto.page, staffFilterDto.size));
        else if (staffFilterDto.page != null)
            page = staffService.getAllStaff(filter, PageRequest.of(staffFilterDto.page, 20));
        else if (staffFilterDto.size != null)
            page = staffService.getAllStaff(filter, PageRequest.of(0, staffFilterDto.size));
        else
            page = staffService.getAllStaff(filter, PageRequest.of(0, 20));
        PageResponse pageResponse = new PageResponse();
        pageResponse.content = page.getContent();
        pageResponse.page = page.getNumber();
        pageResponse.size = page.getSize();
        System.out.println(pageResponse.content);
        return pageResponse;
    }
}

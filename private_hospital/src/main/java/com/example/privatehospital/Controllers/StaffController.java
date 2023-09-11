package com.example.privatehospital.Controllers;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.DTOs.StaffDto;
import com.example.privatehospital.DTOs.StaffFilterDto;
import com.example.privatehospital.DTOs.PageResponse;
import com.example.privatehospital.Entities.*;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Mappers.StaffMapper;
import com.example.privatehospital.Services.StaffService;
import com.example.privatehospital.Services.UserService;
import com.example.privatehospital.StaffFilter;
import org.apache.catalina.connector.Response;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/server/staff")
public class StaffController {
    private final StaffService staffService;
    private final UserService userService;
    private final StaffMapper staffMapper = Mappers.getMapper(StaffMapper.class);

    public StaffController(StaffService staffService, UserService userService) {
        this.staffService = staffService;
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public Integer saveStaff(@RequestBody IdDto idDto){
        staffService.saveStaffById(idDto.id);
        return Response.SC_OK;
    }

    @GetMapping(value = "/{id}")
    public StaffDto getStaffInfo(@PathVariable Long id) throws ParseException {
        Staff staff = staffService.getStaffInfo(id);
        if(staff!=null) {
            staff.setRecords(staffService.getFutureRecords(staff.getRecords()));
            return staffMapper.staffToStaffDto(staff);
        }
        else
            return null;
    }
    @PutMapping
    public Integer updateStaff(@RequestBody Staff staff){
        staffService.saveStaff(staff.setRecords(staffService.getStaffInfo(staff.getId()).getRecords()));
        return Response.SC_OK;
    }
    @GetMapping("/image/{name}")
    public byte[] getStaffImage(@PathVariable String name) throws IOException {
        File serverFile = staffService.getStaffImage(name);
        return Files.readAllBytes(serverFile.toPath());
    }

    @PostMapping("/all/filter")
    public PageResponse getAllStaff(@RequestBody StaffFilterDto staffFilterDto) {
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
        return new PageResponse().setContent(staffMapper.arrayStaffToArrayStaffDto(page.getContent())).setPage(page.getNumber()).setSize(page.getSize());
    }
    @PostMapping(value = "/{id}")
    public Integer addRecord(@PathVariable Long id, @RequestBody IdDto idDto){
        Record record = new Record().setVisitDate(idDto.smth_needed);
        Staff staff = staffService.getStaffInfo(id);
        staffService.saveStaff(staff);
        User user = userService.getUserInfo(idDto.id);
        record.setStaff(staff).setUser(user);
        List<Record> records = staff.getRecords();
        records.add(record);
        staff.setRecords(records);
        staffService.saveStaff(staff);
        return Response.SC_OK;
    }
    @GetMapping("/comment/{id}")
    public List<Comment> getAllComments(@PathVariable Long id){
        return staffService.getAllComment(id);
    }
}

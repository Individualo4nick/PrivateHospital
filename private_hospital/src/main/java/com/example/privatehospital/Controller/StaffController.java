package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.DTOs.StaffDto;
import com.example.privatehospital.DTOs.StaffFilterDto;
import com.example.privatehospital.DTOs.PageResponse;
import com.example.privatehospital.Entities.ClientRecord;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import com.example.privatehospital.Entities.User;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/server")
public class StaffController {
    private final StaffService staffService;
    private final UserService userService;
    private final StaffMapper staffMapper = Mappers.getMapper(StaffMapper.class);

    public StaffController(StaffService staffService, UserService userService) {
        this.staffService = staffService;
        this.userService = userService;
    }

    @PostMapping(value = "/staff/registration")
    public Integer saveStaff(@RequestBody IdDto idDto){
        staffService.saveStaffById(idDto.id);
        return Response.SC_OK;
    }

    @GetMapping(value = "/staff/{id}")
    public StaffDto getStaffInfo(@PathVariable Long id) throws ParseException {
        Staff staff = staffService.getStaffInfo(id);
        staff.setClientRecords(staffService.getFutureRecords(staff.getClientRecords()));
        return staffMapper.staffToStaffDto(staff);
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
    @PostMapping(value = "/staff/{id}")
    public Integer addRecord(@PathVariable Long id, @RequestBody IdDto idDto){
        ClientRecord clientRecord = new ClientRecord().setDate(idDto.smth_needed).setUser(userService.getUserInfo(idDto.id));
        Staff staff = staffService.getStaffInfo(id);
        List<ClientRecord> clientRecords = staff.getClientRecords();
        clientRecords.add(clientRecord);
        staff.setClientRecords(clientRecords);
        staffService.saveStaff(staff);
        Record record = new Record().setVisitDate(idDto.smth_needed).setStaff(staff);
        User user = userService.getUserInfo(idDto.id);
        List<Record> records = user.getRecords();
        records.add(record);
        user.setRecords(records);
        userService.saveUser(user);
        return Response.SC_OK;
    }
}

package com.example.privatehospital.Controllers;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.DTOs.UserDto;
import com.example.privatehospital.Entities.Comment;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Mappers.RecordMapper;
import com.example.privatehospital.Mappers.UserMapper;
import com.example.privatehospital.Services.StaffService;
import com.example.privatehospital.Services.UserService;

import org.apache.catalina.connector.Response;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;

@RestController
@RequestMapping("/server/user")
public class UserController {
    @Value("${files.path}")
    private String imagePath;
    private final UserService userService;
    private final StaffService staffService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private final RecordMapper recordMapper = Mappers.getMapper(RecordMapper.class);

    public UserController(UserService userService, StaffService staffService) {
        this.userService = userService;
        this.staffService = staffService;
    }

    @PostMapping("/registration")
    public Integer saveUser(@RequestBody IdDto idDto){
        userService.saveUserById(idDto.id);
        return Response.SC_CREATED;
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@PathVariable Long id) throws ParseException {
        User user = userService.getUserInfo(id);
        user.setRecords(staffService.getFutureRecords(user.getRecords()));
        return userMapper.userToUserDto(user);
    }
    @PatchMapping
    public Integer updateUser(@RequestBody User user){
        userService.saveUser(user.setRecords(userService.getUserInfo(user.getId()).getRecords()));
        return Response.SC_OK;
    }
    @GetMapping("/image/{name}")
    public byte[] getUserImage(@PathVariable String name) throws IOException {
        File serverFile = userService.getUserImage(name);
        return Files.readAllBytes(serverFile.toPath());
    }
    @GetMapping("/medical_card/{id}")
    public UserDto getMedicalCard(@PathVariable Long id){
        User user = userService.getUserInfo(id);
        return userMapper.userToUserDto(user);
    }
    @PostMapping("/add_comment/{id}")
    public Integer addComment(@PathVariable Long id, @RequestBody Comment comment){
        User user = userService.getUserInfo(id);
        comment.setUsername(user.getName());
        userService.addComment(comment);
        return Response.SC_OK;
    }
    @GetMapping("/record/{id}")
    public RecordDto getRecord(@PathVariable Long id){
        return recordMapper.recordToRecordDto(userService.getRecordByClientRecordId(id));
    }
    @PatchMapping("/record/{id}")
    public Integer updateRecord(@RequestBody RecordDto recordDto){
        userService.updateRecord(recordDto);
        return Response.SC_OK;
    }
}

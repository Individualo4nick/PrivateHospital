package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.DTOs.UserDto;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Mappers.UserMapper;
import com.example.privatehospital.Services.UserService;

import org.apache.catalina.connector.Response;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping("/server")
public class UserController {
    @Value("${files.path}")
    private String imagePath;
    private final UserService userService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/registration")
    public Integer saveUser(@RequestBody IdDto idDto){
        userService.saveUserById(idDto.id);
        return Response.SC_OK;
    }

    @GetMapping(value = "/user/{id}")
    public UserDto getUserInfo(@PathVariable Long id) throws ParseException {
        User user = userService.getUserInfo(id);
        user.setRecords(userService.getFutureRecords(user.getRecords()));
        return userMapper.userToUserDto(user);
    }
    @PutMapping(value = "/user")
    public Integer updateUser(@RequestBody User user){
        userService.saveUser(user.setRecords(userService.getUserInfo(user.getId()).getRecords()));
        return Response.SC_OK;
    }
    @GetMapping("/user_image/{name}")
    @ResponseBody
    public byte[] getUserImage(@PathVariable String name) throws IOException {
        File serverFile = userService.getUserImage(name);
        return Files.readAllBytes(serverFile.toPath());
    }
}

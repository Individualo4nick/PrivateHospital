package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.DTOs.UserDto;
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
import java.text.ParseException;

@RestController
@RequestMapping("/server/user")
public class UserController {
    @Value("${files.path}")
    private String imagePath;
    private final UserService userService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public Integer saveUser(@RequestBody IdDto idDto){
        userService.saveUserById(idDto.id);
        return Response.SC_OK;
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@PathVariable Long id) throws ParseException {
        User user = userService.getUserInfo(id);
        user.setRecords(userService.getFutureRecords(user.getRecords()));
        return userMapper.userToUserDto(user);
    }
    @PutMapping
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
}

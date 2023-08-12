package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Services.UserService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/server")
public class UserController {
    @Value("${files.path}")
    private String imagePath;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/registration")
    public Integer saveUser(@RequestBody IdDto idDto){
        userService.saveUserById(idDto.id);
        return Response.SC_OK;
    }

    @GetMapping(value = "/user/{id}")
    public User getUserInfo(@PathVariable Long id){
        return userService.getUserInfo(id);
    }
    @PutMapping(value = "/user")
    public Integer updateUser(@RequestBody User user){
        userService.saveUser(user);
        return Response.SC_OK;
    }
    @GetMapping("/user_image/{name}")
    @ResponseBody
    public byte[] getUserImage(@PathVariable String name) throws IOException {
        File serverFile = userService.getUserImage(name);
        return Files.readAllBytes(serverFile.toPath());
    }
}

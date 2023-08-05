package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.Services.UserService;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/registration")
    public Integer saveUser(@RequestBody IdDto idDto){
        userService.saveUserById(idDto.id);
        return Response.SC_OK;
    }
}

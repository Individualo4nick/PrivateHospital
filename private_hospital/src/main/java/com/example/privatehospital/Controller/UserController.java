package com.example.privatehospital.Controller;

import com.example.privatehospital.DTOs.IdDto;
import com.example.privatehospital.Entities.User;
import com.example.privatehospital.Services.UserService;

import org.apache.catalina.connector.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
public class UserController {
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
}

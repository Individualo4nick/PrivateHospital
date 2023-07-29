package com.example.authorization.controller;


import com.example.authorization.controller.exc.AuthException;
import com.example.authorization.domain.entity.User;
import com.example.authorization.domain.jwt.JwtRequest;
import com.example.authorization.domain.jwt.JwtResponse;
import com.example.authorization.domain.jwt.RefreshJwtRequest;
import com.example.authorization.service.AuthService;
import com.example.authorization.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(JwtRequest authRequest, HttpServletResponse response) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        Cookie cookie = new Cookie("tokens", token.toString());
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return "redirect:/api/auth/main";
    }

    @GetMapping("/registration")
    public String getRegistration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(User user){
        userService.saveUser(user);
        return "login";
    }

}

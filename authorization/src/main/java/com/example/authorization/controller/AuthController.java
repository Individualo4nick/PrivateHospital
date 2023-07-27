package com.example.authorization.controller;


import com.example.authorization.controller.exc.AuthException;
import com.example.authorization.domain.entity.User;
import com.example.authorization.domain.jwt.JwtRequest;
import com.example.authorization.domain.jwt.JwtResponse;
import com.example.authorization.domain.jwt.RefreshJwtRequest;
import com.example.authorization.service.AuthService;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @GetMapping("registration")
    public String getRegistration(){
        return "registration";
    }

    @PostMapping("registration")
    public String postRegistration(User user){
        userService.saveUser(user);
        return "login";
    }

}

package com.example.authorization.controller;


import com.example.authorization.controller.exc.AuthException;
import com.example.authorization.domain.entity.Enums.Role;
import com.example.authorization.domain.jwt.JwtRequest;
import com.example.authorization.domain.jwt.JwtResponse;
import com.example.authorization.dtos.IdDto;
import com.example.authorization.dtos.UserRegisterDto;
import com.example.authorization.service.AuthService;
import com.example.authorization.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final WebClient webClient = WebClient.create("http://localhost:8888");


    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model, @ModelAttribute("authRequest") JwtRequest authRequest) {
        model.addAttribute("authRequest", authRequest);
        return "login";
    }

    @PostMapping("/login")
    public String login(@Validated JwtRequest authRequest, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, HttpServletResponse response) throws AuthException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("authRequest", authRequest);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/login";
        } else {
            final JwtResponse token;
            try {
                token = authService.login(authRequest);
            }
            catch (AuthException authException){
                throw authException;
            }
            Cookie cookie = new Cookie("access", token.getAccessToken());
            cookie.setMaxAge(3600);
            cookie.setHttpOnly(true);
            Cookie cookie1 = new Cookie("refresh", token.getRefreshToken());
            cookie1.setMaxAge(3600);
            cookie1.setHttpOnly(true);
            response.addCookie(cookie);
            response.addCookie(cookie1);
            return "redirect:/main";
        }
    }

    @GetMapping("/registration")
    public String getRegistration(Model model, @ModelAttribute("user") UserRegisterDto userReg) {
        model.addAttribute("user", userReg);
        model.addAttribute("roles", Role.getRoles());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String postRegistration(@Validated UserRegisterDto userReg, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userReg);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }
        userService.saveUser(userReg);
        IdDto idDto = new IdDto();
        idDto.id = userService.getByLogin(userReg.getLogin()).get().getId();
        webClient.post()
                .uri("/server/" + userReg.getRole().toString().toLowerCase() + "/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(idDto), IdDto.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        return "redirect:/login";
    }
}
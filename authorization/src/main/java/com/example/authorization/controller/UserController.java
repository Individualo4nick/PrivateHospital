package com.example.authorization.controller;

import com.example.authorization.dtos.IdDto;
import com.example.authorization.dtos.UserInfoDto;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController  {


    private final UserService userService;
    private final WebClient webClient = WebClient.create("http://localhost:8888");


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile")
    public String profilePage(Model model){
        IdDto idDto = new IdDto();
        idDto.id = userService.getUserId();
        UserInfoDto userInfoDto = webClient.get()
                .uri("/server/user/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("user", userInfoDto);
        return "profile";
    }
    @PostMapping("/edit_profile")
    public String editProfilePage(UserInfoDto userInfoDto){
        userInfoDto.id = userService.getUserId();
        webClient.put()
                .uri("/server/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userInfoDto), UserInfoDto.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        return "redirect:/api/user/profile";
    }
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/edit_profile")
    public String getEditProfilePage(Model model, @ModelAttribute("userInfoDto") UserInfoDto userInfoDto){
        IdDto idDto = new IdDto();
        idDto.id = userService.getUserId();
        userInfoDto = webClient.get()
                .uri("/server/user/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("userInfoDto", userInfoDto);
        return "edit";
    }
}


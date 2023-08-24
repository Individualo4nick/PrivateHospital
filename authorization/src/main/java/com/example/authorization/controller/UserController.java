package com.example.authorization.controller;

import com.example.authorization.dtos.UserInfoDto;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Controller
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController  {


    private final UserService userService;
    private WebClient webClient = WebClient.create("http://localhost:8888");


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile")
    public String profilePage(Model model){
        UserInfoDto userInfoDto = webClient.get()
                .uri("/server/user/" + userService.getUserId().toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("user", userInfoDto);
        model.addAttribute("id", userInfoDto.id);
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
        userInfoDto = webClient.get()
                .uri("/server/user/" + userService.getUserId().toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("userInfoDto", userInfoDto);
        return "edit";
    }
    @GetMapping("/user_image/{name}")
    @ResponseBody
    public byte[] getUserImage(@PathVariable String name) {
        return webClient.get()
                .uri("/server/user/image/" + name)
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray).block();
    }
    @GetMapping("/profile/medical_card")
    public String getMedicalCard(Model model){
        UserInfoDto userInfoDto = webClient.get()
                .uri("/server/user/medical_card/" + userService.getUserId().toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("user", userInfoDto);
        return "medical_card";
    }
}
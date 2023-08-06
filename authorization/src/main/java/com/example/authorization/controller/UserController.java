package com.example.authorization.controller;

import com.example.authorization.dtos.IdDto;
import com.example.authorization.dtos.UserInfoDto;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController  {

    private final UserService userService;
    private WebClient webClient = WebClient.create("http://localhost:8888");

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/profile")
    public String profilePage(Model model){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        IdDto idDto = new IdDto();
        idDto.id = userService.getByLogin(login).get().getId();
        UserInfoDto userInfoDto = webClient.get()
                .uri("/server/user/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("user", userInfoDto);
        return "profile";
    }


}


package com.example.authorization.controller;

import com.example.authorization.dtos.IdDto;
import com.example.authorization.dtos.UserInfoDto;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

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
                .uri("/server/users/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("user", userInfoDto);
        return "profile";
    }
    @PostMapping("/edit_profile")
    public String editProfilePage(UserInfoDto userInfoDto){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        userInfoDto.id = userService.getByLogin(login).get().getId();
        webClient.put()
                .uri("/server/users")
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
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        IdDto idDto = new IdDto();
        idDto.id = userService.getByLogin(login).get().getId();
        userInfoDto = webClient.get()
                .uri("/server/users/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(UserInfoDto.class)
                .block();
        model.addAttribute("userInfoDto", userInfoDto);
        return "edit";
    }
}


package com.example.authorization.controller;

import com.example.authorization.dtos.IdDto;
import com.example.authorization.dtos.StaffDto;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final UserService userService;
    private WebClient webClient = WebClient.create("http://localhost:8888");

    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/profile")
    public String profilePage(Model model){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        IdDto idDto = new IdDto();
        idDto.id = userService.getByLogin(login).get().getId();
        StaffDto staffDto = webClient.get()
                .uri("/server/staff/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        model.addAttribute("staff", staffDto);
        return "staff_profile";
    }
    @PostMapping("/edit_profile")
    public String editProfilePage(StaffDto staffDto){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        staffDto.id = userService.getByLogin(login).get().getId();
        webClient.put()
                .uri("/server/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(staffDto), StaffDto.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        return "redirect:/api/staff/profile";
    }
    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/edit_profile")
    public String getEditProfilePage(Model model, @ModelAttribute("staffDto") StaffDto staffDto){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        IdDto idDto = new IdDto();
        idDto.id = userService.getByLogin(login).get().getId();
        staffDto = webClient.get()
                .uri("/server/staff/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        model.addAttribute("staffDto", staffDto);
        return "staff_edit";
    }
}



package com.example.authorization.controller;

import com.example.authorization.dtos.*;
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
@RequestMapping("api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final UserService userService;
    private WebClient webClient = WebClient.create("http://localhost:8888");


    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/profile")
    public String profilePage(Model model){
        IdDto idDto = new IdDto();
        idDto.id = userService.getUserId();
        StaffDto staffDto = webClient.get()
                .uri("/server/staff/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        model.addAttribute("staff", staffDto);
        model.addAttribute("id", staffDto.id);
        return "staff_profile";
    }
    @PostMapping("/edit_profile")
    public String editProfilePage(StaffDto staffDto){
        staffDto.id = userService.getUserId();
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
        IdDto idDto = new IdDto();
        idDto.id = userService.getUserId();
        staffDto = webClient.get()
                .uri("/server/staff/" + idDto.id.toString())
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        model.addAttribute("staffDto", staffDto);
        return "staff_edit";
    }
    @GetMapping("/staff_image/{name}")
    @ResponseBody
    public byte[] getUserImage(@PathVariable String name) throws IOException {
        return webClient.get()
                .uri("/server/staff_image/" + name)
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray).block();
    }
    @GetMapping("/all")
    public String getAllInfo(StaffFilterDto staffFilterDto, Model model) {
        PageResponse pageResponse = webClient.post()
                .uri("/server/staff/all/filter")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(staffFilterDto), StaffFilterDto.class)
                .retrieve()
                .bodyToMono(PageResponse.class)
                .block();
        model.addAttribute("staffFilterDto", staffFilterDto);
        model.addAttribute("staff", pageResponse);
        return "get_all_staff";
    }
}



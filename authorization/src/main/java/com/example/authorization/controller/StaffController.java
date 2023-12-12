package com.example.authorization.controller;

import com.example.authorization.dtos.*;
import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final UserService userService;
    private final WebClient webClient = WebClient.create("http://privatehospital:8888");


    @PreAuthorize("hasAuthority('STAFF')")
    @GetMapping("/profile")
    public String profilePage(Model model){
        StaffDto staffDto = webClient.get()
                .uri("/server/staff/" + userService.getUserId().toString())
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        model.addAttribute("staff", staffDto);
        model.addAttribute("id", userService.getUserId());
        return "staff_profile";
    }
    @PostMapping("/edit_profile")
    public String editProfilePage(StaffDto staffDto){
        staffDto.id = userService.getUserId();
        webClient.patch()
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
        staffDto = webClient.get()
                .uri("/server/staff/" + userService.getUserId().toString())
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        model.addAttribute("staffDto", staffDto);
        return "staff_edit";
    }
    @GetMapping("/image/{name}")
    @ResponseBody
    public byte[] getUserImage (@PathVariable String name){
        return webClient.get()
                .uri("/server/staff/image/" + name)
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray).block();
    }
    @GetMapping("/all")
    public String getAllInfoAllStaff(StaffFilterDto staffFilterDto, Model model) {
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
    @GetMapping("/{id}")
    public String getAllInfoOneStaff(@PathVariable String id, Model model) {
        StaffDto staffDto = webClient.get()
                .uri("/server/staff/"+id)
                .retrieve()
                .bodyToMono(StaffDto.class)
                .block();
        if (staffDto==null)
            return "not_found";
        List<CommentDto> commentDtoList = webClient.get()
                        .uri("/server/staff/comment/"+id)
                        .retrieve()
                        .bodyToFlux(CommentDto.class)
                        .collectList()
                        .block();
        model.addAttribute("staffDto", staffDto);
        model.addAttribute("comments", commentDtoList);
        return "staff_profile_for_user";
    }
    @PostMapping("/{id}")
    public String makeAppointment(@PathVariable String id, String recordDate) {
        IdDto idDto = new IdDto();
        idDto.id = userService.getUserId();
        idDto.smth_needed = recordDate;
        webClient.post()
                .uri("/server/staff/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(idDto), IdDto.class)
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        return "redirect:/api/staff/"+id;
    }
}
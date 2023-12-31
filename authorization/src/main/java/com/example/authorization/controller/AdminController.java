package com.example.authorization.controller;

import com.example.authorization.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WebClient webClient = WebClient.create("http://privatehospital:8888");
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete_staff_page/{id}")
    public String deleteStaffPage(@PathVariable Long id) {
        Integer a = webClient.delete()
                .uri("/server/admin/delete_staff/" + id.toString())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        userService.deleteUser(id);
        return "redirect:/api/staff/all";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete_comment/{id}")
    public String deleteComment(@PathVariable Long id, Long idStaff) {
        Integer a = webClient.delete()
                .uri("/server/admin/delete_comment/" + id.toString())
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
        return "redirect:/api/staff/"+idStaff.toString();
    }
}

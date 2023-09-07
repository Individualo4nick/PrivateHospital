package com.example.authorization.handler;

import com.example.authorization.controller.exc.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@ControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(AuthException.class)
    public String handleAuthException(AuthException exception, RedirectAttributes redirectAttributes){
        log.error(exception.getMessage());
        redirectAttributes.addFlashAttribute("auth_error", "Неправильный логин или пароль");
        return "redirect:/login";
    }
}

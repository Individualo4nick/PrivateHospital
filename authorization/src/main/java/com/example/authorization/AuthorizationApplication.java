package com.example.authorization;

import com.example.authorization.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorizationApplication {

    public static void main(String[] args) {
        System.out.println(SecurityConfig.passwordEncoder().encode("wtf123"));;
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}

package com.example.authorization.domain.jwt;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    @NotNull
    @Size(min = 3, max = 64)
    private String login;
    @NotNull
    @Size(min = 6, max= 20)
    private String password;
}

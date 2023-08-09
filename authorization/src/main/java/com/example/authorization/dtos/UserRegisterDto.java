package com.example.authorization.dtos;

import com.example.authorization.domain.entity.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {
    @NotNull
    @Size(min = 3, max = 64)
    public String login;
    @NotNull
    @Size(min = 6, max= 20)
    public String password;
    public Role role;
}

package com.example.authorization.domain.entity;

import com.example.authorization.domain.entity.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "users")
@Getter
@Setter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", unique = true, nullable = false)
    @NotNull
    @Size(min = 3, max = 64)
    private String login;
    @NotNull
    @Size(min = 6, max= 20)
    private String password;
    private Role role;
}

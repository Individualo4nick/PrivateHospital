package com.example.authorization.domain.entity;

import com.example.authorization.domain.entity.Enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "login", unique = true, nullable = false)
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}

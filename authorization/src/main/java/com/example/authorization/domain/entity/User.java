package com.example.authorization.domain.entity;

import com.example.authorization.domain.entity.Enums.Role;
import jakarta.persistence.*;
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
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String password;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    private Role role;
}

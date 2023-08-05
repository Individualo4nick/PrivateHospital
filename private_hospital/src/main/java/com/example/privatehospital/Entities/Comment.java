package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="comments")
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String text;

}

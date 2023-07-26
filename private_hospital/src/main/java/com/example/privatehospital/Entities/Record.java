package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "visit_date")
    private String visitDate;
    private String description;
    private String service;
    private Integer price;
    @Column(name = "staff_id")
    private Long staffId;
    @Column(name = "user_id")
    private Long userId;
}

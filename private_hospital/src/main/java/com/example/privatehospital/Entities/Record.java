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
    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}

package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "client_records")
@Getter
@Setter
@Accessors(chain = true)
public class ClientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "staff_id")
//    private Staff staff;
}

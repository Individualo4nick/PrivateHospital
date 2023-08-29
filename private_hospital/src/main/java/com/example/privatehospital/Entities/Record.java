package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "records")
@Getter
@Setter
@Accessors(chain = true)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String visitDate;
    private String description;
    private String service;
    private Integer price;
    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
    private Long clientRecordId;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
}

package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
public class Staff {
    @Id
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String position;
    private String department;
    @OneToMany(mappedBy = "staff")
    private List<ClientRecord> clientRecords;

}

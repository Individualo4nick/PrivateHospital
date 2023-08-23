package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@Accessors(chain = true)
public class Staff {
    @Id
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String position="";
    private String department="";
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "staff_id")
    private List<ClientRecord> clientRecords;

}

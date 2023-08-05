package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    private Long id;
    private String name;
    private String surname;
    @OneToMany(mappedBy = "user")
    private List<Record> records;
}

package com.example.privatehospital.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Accessors(chain = true)
public class User extends AuditionEntity{
    @Id
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String birthDate="";
    @OneToMany(mappedBy = "user")
    private List<Record> records;
}

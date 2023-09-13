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
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "staff", fetch = FetchType.EAGER)
    private List<Record> records;
    @PreRemove
    private void preRemove(){
        for(Record record : records){
            record.setStaff(null);
        }
    }

}

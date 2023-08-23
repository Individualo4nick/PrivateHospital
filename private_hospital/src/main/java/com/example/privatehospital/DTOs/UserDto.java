package com.example.privatehospital.DTOs;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    public Long id;
    public String email;
    public String name;
    public String surname;
    public String birthDate;
    public List<RecordDto> records;
}

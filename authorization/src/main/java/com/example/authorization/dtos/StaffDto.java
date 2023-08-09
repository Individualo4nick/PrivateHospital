package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StaffDto {
    public Long id;
    public String email;
    public String name;
    public String surname;
    public String position;
    public String department;
    public List<ClientRecordDto> records;
}

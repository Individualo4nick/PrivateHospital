package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordDto {
    public Long id;
    public String visitDate;
    public String description;
    public String service;
    public Integer price;
    public String staff_name;
    public String staff_surname;
    public String staff_position;
    public String staff_department;
    public String user_name;
    public String user_surname;
    public Long user_id;
}

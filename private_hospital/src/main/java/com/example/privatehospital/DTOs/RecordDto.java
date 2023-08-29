package com.example.privatehospital.DTOs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
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
    public Long client_record_id;
}

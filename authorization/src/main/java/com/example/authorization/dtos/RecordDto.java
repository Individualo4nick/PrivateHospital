package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordDto {
    public String visitDate;
    public String description;
    public String service;
    public Integer price;
    public StaffDto staff;
}

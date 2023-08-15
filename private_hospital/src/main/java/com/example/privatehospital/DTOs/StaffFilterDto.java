package com.example.privatehospital.DTOs;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StaffFilterDto {
    public String position;
    public String department;
    public Integer page;
    public Integer size;
}

package com.example.privatehospital.DTOs;

import com.example.privatehospital.Entities.Staff;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse {
    public List<Staff> content;
    public int page;
    public int size;
    public long totalElements;
}

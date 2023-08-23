package com.example.privatehospital.DTOs;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PageResponse {
    public List<StaffDto> content;
    public int page;
    public int size;
    public long totalElements;
}

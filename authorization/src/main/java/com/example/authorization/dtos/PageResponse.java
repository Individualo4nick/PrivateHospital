package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponse {
    public List<StaffDto> content;
    public int page;
    public int size;
    public long totalElements;
}

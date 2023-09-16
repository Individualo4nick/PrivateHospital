package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

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
//@Value
//public class PageResponse<T> {
//    public List<T> content;
//    Metadata metadata;
//    @Value
//    public static class Metadata{
//        int page;
//        int size;
//        long totalElements;
//    }
//    public static <T> PageResponse<T> of(Page<T> page){
//        var metadata = new Metadata(page.getNumber(), page.getTotalPages(), page.getTotalElements());
//        return new PageResponse<>(page.getContent(), metadata);
//    }
//}

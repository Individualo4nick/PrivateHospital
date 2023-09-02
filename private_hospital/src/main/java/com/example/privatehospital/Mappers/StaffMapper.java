package com.example.privatehospital.Mappers;

import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.DTOs.StaffDto;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StaffMapper {
    @Mapping(target = "records", source = "records")
    StaffDto staffToStaffDto(Staff staff);
    default List<RecordDto> map(List<Record> field) {
        ArrayList<RecordDto> recordDtos = new ArrayList<>();
        if (field!= null) {
            for (Record record : field) {
                recordDtos.add(new RecordDto()
                        .setId(record.getId())
                        .setVisitDate(record.getVisitDate())
                        .setUser_id(record.getUser().getId())
                        .setUser_name(record.getUser().getName())
                        .setUser_surname(record.getUser().getSurname()));
            }
        }
        return recordDtos;
    }
    @IterableMapping(elementTargetType = StaffDto.class)
    List<StaffDto> arrayStaffToArrayStaffDto(Iterable<Staff> staff);
}

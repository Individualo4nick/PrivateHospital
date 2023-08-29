package com.example.privatehospital.Mappers;

import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.DTOs.UserDto;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserMapper {
    @Mapping(target = "records", source = "records")
    UserDto userToUserDto(User user);
    default List<RecordDto> map(List<Record> field) {
        ArrayList<RecordDto> recordDtos = new ArrayList<>();
        if (field!= null) {
            for (Record record : field) {
                recordDtos.add(new RecordDto()
                        .setId(record.getId())
                        .setVisitDate(record.getVisitDate())
                        .setService(record.getService())
                        .setPrice(record.getPrice())
                        .setDescription(record.getDescription())
                        .setStaff_name(record.getStaff().getName())
                        .setStaff_surname(record.getStaff().getSurname())
                        .setStaff_position(record.getStaff().getPosition())
                        .setStaff_department(record.getStaff().getDepartment())
                        .setClient_record_id(record.getClientRecordId()));
            }
        }
        return recordDtos;
    }
}

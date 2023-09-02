package com.example.privatehospital.Mappers;

import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.Entities.Record;
import org.mapstruct.Mapper;

@Mapper
public interface RecordMapper {
    default RecordDto recordToRecordDto(Record record){
        return new RecordDto().setId(record.getId())
                .setVisitDate(record.getVisitDate())
                .setDescription(record.getDescription())
                .setService(record.getService())
                .setPrice(record.getPrice())
                .setStaff_name(record.getStaff().getName())
                .setStaff_surname(record.getStaff().getSurname())
                .setStaff_position(record.getStaff().getPosition())
                .setStaff_department(record.getStaff().getDepartment())
                .setUser_name(record.getUser().getName())
                .setUser_surname(record.getUser().getSurname());
    }
}

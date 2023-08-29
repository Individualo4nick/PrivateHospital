package com.example.privatehospital.Mappers;

import com.example.privatehospital.DTOs.ClientRecordDto;
import com.example.privatehospital.DTOs.RecordDto;
import com.example.privatehospital.DTOs.StaffDto;
import com.example.privatehospital.Entities.ClientRecord;
import com.example.privatehospital.Entities.Record;
import com.example.privatehospital.Entities.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
                .setClient_record_id(record.getClientRecordId());
    }
}

package com.example.privatehospital.Mappers;

import com.example.privatehospital.DTOs.ClientRecordDto;
import com.example.privatehospital.DTOs.StaffDto;
import com.example.privatehospital.Entities.ClientRecord;
import com.example.privatehospital.Entities.Staff;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface StaffMapper {
    @Mapping(target = "records", source = "clientRecords")
    StaffDto staffToStaffDto(Staff staff);
    default List<ClientRecordDto> map(List<ClientRecord> field) {
        ArrayList<ClientRecordDto> clientRecordDtos = new ArrayList<>();
        if (field!= null) {
            for (ClientRecord clientRecord : field) {
                clientRecordDtos.add(new ClientRecordDto()
                        .setId(clientRecord.getId())
                        .setDate(clientRecord.getDate())
                        .setUser_name(clientRecord.getUser().getName())
                        .setUser_surname(clientRecord.getUser().getSurname()));
            }
        }
        return clientRecordDtos;
    }
    @IterableMapping(elementTargetType = StaffDto.class)
    List<StaffDto> arrayStaffToArrayStaffDto(Iterable<Staff> staff);
}

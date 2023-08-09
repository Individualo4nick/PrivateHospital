package com.example.authorization.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserInfoDto {
    public Long id;
    public String email;
    public String name;
    public String surname;
    public String birthDate;
    public List<RecordDto> records;
}

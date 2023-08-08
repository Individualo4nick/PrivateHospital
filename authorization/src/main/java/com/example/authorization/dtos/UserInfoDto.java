package com.example.authorization.dtos;


import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoDto {
    public Long id;
    @Email
    public String email;
    public String name;
    public String surname;
    public List<RecordDto> records;
}

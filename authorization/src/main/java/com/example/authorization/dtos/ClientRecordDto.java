package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRecordDto {
    public Long id;
    public String date;
    public Long user_id;
    public String user_name;
    public String user_surname;
}


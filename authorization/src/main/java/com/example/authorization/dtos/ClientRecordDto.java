package com.example.authorization.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRecordDto {
    public long id;
    public String date;
    public String user_name;
    public String user_surname;
}


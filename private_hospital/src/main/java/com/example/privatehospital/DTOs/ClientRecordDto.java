package com.example.privatehospital.DTOs;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ClientRecordDto {
    public long id;
    public String date;
    public long user_id;
    public String user_name;
    public String user_surname;
}


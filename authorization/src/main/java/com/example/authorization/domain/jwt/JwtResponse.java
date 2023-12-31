package com.example.authorization.domain.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
    @Override
    public String toString(){
        return "--TYPE--"+type+"--ACCESS--"+accessToken+"--REFRESH--"+refreshToken;
    }
}

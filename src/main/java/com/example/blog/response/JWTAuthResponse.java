package com.example.blog.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String expireDate;
    private String tokenType = "Bearer";

    public JWTAuthResponse(String accessToken, String expireDate) {
        this.accessToken = accessToken;
        this.expireDate = expireDate;
    }
}

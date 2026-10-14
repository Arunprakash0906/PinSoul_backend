package com.example.PinSoul.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignResponse {
    private String message;
    private Integer signId;
    private String username;
    private String category;
    private String token;

    public SignResponse(String message, Integer signId, String username, String category, String token) {
        this.message = message;
        this.signId = signId;
        this.username = username;
        this.category = category;
        this.token = token;
    }
}

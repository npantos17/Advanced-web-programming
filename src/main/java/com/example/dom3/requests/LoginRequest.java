package com.example.dom3.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}

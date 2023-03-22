package com.example.dom3.responses;

import com.example.dom3.models.Permission;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String jwt;
    private List<Permission> permissions;

    public LoginResponse(String jwt, List<Permission> permissions) {

        this.jwt = jwt;
        this.permissions = permissions;

    }
}

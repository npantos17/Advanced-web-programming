package com.example.dom3.models;

import lombok.Data;

@Data
public class UserUpdateReq {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Permission[] permissions;
}

package com.example.demo.entity;

import lombok.Data;

@Data
public class Admin {
    private int id;
    private String username;
    private String password;

    private String role;
}

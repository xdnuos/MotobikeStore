package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Role;

import java.util.UUID;

public class BaseUserResponse {
    private UUID uuid;
    private String email;
    private String fullName;
    private Role role;
}

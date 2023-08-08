package com.example.motobikestore.DTO.auth;

import com.example.motobikestore.DTO.user.UsersResponse;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private UsersResponse user;
    private String token;
}
package com.example.motobikestore.DTO;

import com.example.motobikestore.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {
    private Role role;
}

package com.example.motobikestore.DTO.user;

import com.example.motobikestore.DTO.PermissionDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class AccountDTO implements Serializable {
    public String email;
    public PermissionDTO permisson;

    public AccountDTO(String email, PermissionDTO permisson) {
        this.email = email;
        this.permisson = permisson;
    }
}

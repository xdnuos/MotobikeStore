package com.example.motobikestore.DTO.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class StaffDTO implements Serializable{
    private UUID staffID;
    private String fullName;
    private AccountDTO accountDTO;
}

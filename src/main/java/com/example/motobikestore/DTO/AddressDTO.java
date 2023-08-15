package com.example.motobikestore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.Address}
 */
@Getter
@Setter
public class AddressDTO implements Serializable {
    private long addressID;
    private String address;
    private String phone;
    private String fullname;
    private UUID userID;
}
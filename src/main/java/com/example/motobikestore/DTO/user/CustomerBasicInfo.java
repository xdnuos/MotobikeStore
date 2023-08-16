package com.example.motobikestore.DTO.user;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.Customer}
 */
@Data
public class CustomerBasicInfo implements Serializable {
    private UUID customerID;
    private String phone;
    private String firstName;
    private String lastName;

    public CustomerBasicInfo() {
    }

    public CustomerBasicInfo(UUID customerID, String phone, String firstName, String lastName) {
        this.customerID = customerID;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Sex;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.Customer}
 */
@Data
public class CustomerInfo implements Serializable {
    private UUID customerID;
    private String phone;
    private String firstName;
    private String lastName;

    public CustomerInfo() {
    }

    public CustomerInfo(UUID customerID, String phone, String firstName, String lastName) {
        this.customerID = customerID;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
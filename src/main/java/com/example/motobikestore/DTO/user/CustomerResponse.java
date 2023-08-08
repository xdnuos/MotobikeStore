package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.example.motobikestore.entity.Customer}
 */
@Getter
@Setter
public class CustomerResponse extends UsersResponse {
    @Pattern(message = "Phone number must be 9 or 10 digits", regexp = "\\d{9}|\\d{10}")
    private String phone;
    @NotNull(message = "Gender cannot be null")
    private Sex sex;
    private LocalDate birth;
}
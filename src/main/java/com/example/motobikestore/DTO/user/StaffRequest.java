package com.example.motobikestore.DTO.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.example.motobikestore.entity.Staff}
 */
@AllArgsConstructor
@Getter
public class StaffRequest extends UsersRequest {
    private final boolean sex;
    @Pattern(message = "Phone number must be 9 or 10 digits", regexp = "\\d{9}|\\d{10}")
    private final String phone;
    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private final Date birth;
    @Pattern(message = "Identity ID must be 9 or 12 digits", regexp = "\\d{9}|\\d{12}")
    private final String cccd;
}
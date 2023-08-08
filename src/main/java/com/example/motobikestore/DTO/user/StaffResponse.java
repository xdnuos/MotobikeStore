package com.example.motobikestore.DTO.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.Staff}
 */
@Getter
@Setter
public class StaffResponse extends UsersResponse {
    private boolean sex;
    @Pattern(message = "Phone number must be 9 or 10 digits", regexp = "\\d{9}|\\d{10}")
    private String phone;
    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birth;
    @Pattern(message = "Identity ID must be 9 or 12 digits", regexp = "\\d{9}|\\d{12}")
    private String cccd;
    private UUID managerUsersUserID;
    private String managerUsersLastName;
}
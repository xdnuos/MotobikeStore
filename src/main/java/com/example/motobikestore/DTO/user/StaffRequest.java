package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.Staff}
 */
@Data
public class StaffRequest extends UsersRequest {
    private UUID staffID;
    private Sex sex;
    @Pattern(message = "Phone number must be 9 or 10 digits", regexp = "\\d{9}|\\d{10}")
    private String phone;
    @NotNull(message = "Birth date cannot be null")
    @Past(message = "Birth date must be in the past")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birth;
    @Pattern(message = "Identity ID must be 9 or 12 digits", regexp = "\\d{9}|\\d{12}")
    private String cccd;
    private UUID managerID;
}
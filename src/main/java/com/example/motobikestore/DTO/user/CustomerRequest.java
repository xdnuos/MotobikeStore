package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.motobikestore.entity.Customer}
 */
@Getter
public class CustomerRequest extends UsersRequest {
    @Pattern(message = "Phone number must be 9 or 10 digits", regexp = "\\d{9}|\\d{10}")
    private String phone;
    @NotNull(message = "Gender cannot be null")
    private Sex sex;
    @DateTimeFormat(pattern ="yyyy-MM-dd" )
    private LocalDate birth;
    private String address;

    public CustomerRequest() {
    }

    public CustomerRequest(String phone, Sex sex, LocalDate birth, String address) {
        this.phone = phone;
        this.sex = sex;
        this.birth = birth;
        this.address = address;
    }
}
package com.example.motobikestore.DTO.user;

import com.example.motobikestore.entity.Images;
import com.example.motobikestore.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.Users}
 */
@Data
public class UsersResponse implements Serializable {
    private UUID userID;
    @Email
    private String email;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private String fullName;
    private Set<Role> roles;
    private Boolean isActive;
    private LocalDateTime createDate;
    private String avatarUrl;
    public String getFullName() {
        if (firstName == null && lastName == null) {
            return "";
        } else if (firstName == null) {
            return lastName;
        } else if (lastName == null) {
            return firstName;
        } else {
            return firstName + " " + lastName;
        }
    }
}
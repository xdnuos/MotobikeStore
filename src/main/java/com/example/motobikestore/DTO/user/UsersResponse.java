package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Getter
@Setter
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
    public String getFullName() {
        return firstName+" "+lastName;
    }
}
package com.example.motobikestore.DTO.user;

import com.example.motobikestore.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.motobikestore.entity.Users}
 */
@Getter
public class UsersRequest implements Serializable {
    @Email
    private String email;
    private String password;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    private Role role;
}
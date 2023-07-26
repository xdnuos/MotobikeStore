package com.example.motobikestore.entity;

import com.example.motobikestore.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userID;

    @Email
    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @NotBlank(message="Full name cannot be blank")
    @Pattern(regexp="^[\\p{L} \\.'\\-]+$", message="Full name must contain only letters and spaces")
    @Column(name = "fullname", length = 225)
    private String fullname;
    @Column
    private boolean active;

    @Column
    private LocalDateTime createDate;
    private Set<Role> roles;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "password_reset_code")
    private String passwordResetCode;
}

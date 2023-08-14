package com.example.motobikestore.entity;

import com.example.motobikestore.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Table
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userID;

    @Email
    @Column(unique = true)
    private String email;

    @Column
    private String password;

//    @NotBlank(message="First name cannot be blank")
    @Column(name = "firstName", length = 50)
    private String firstName;

    @NotBlank(message="Last name cannot be blank")
    @Column(name = "lastName", length = 50)
    private String lastName;
    @Column
    private boolean active;

    @Column
    private LocalDateTime createDate;
    private Set<Role> roles;

    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "password_reset_code")
    private String passwordResetCode;

    @Column(name = "reset_passcode_create")
    private LocalDateTime resetPassCodeCreate;
    @Transient
    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_image_id")
    private Images avatar;
}

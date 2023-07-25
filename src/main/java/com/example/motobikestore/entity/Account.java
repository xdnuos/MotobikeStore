package com.example.motobikestore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountID;

    @Email
    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private boolean status;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "permissonID")
    private Permisson permission;
}

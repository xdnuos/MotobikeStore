package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table
public class Password_reset_token implements Serializable {
    @Id
    private long Id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;
}

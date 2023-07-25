package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long addressID;

    private String address;

    private String phone;

    private String fullname;

    @ManyToOne
    @JoinColumn(name = "customerID")
    private Customer customer;
}

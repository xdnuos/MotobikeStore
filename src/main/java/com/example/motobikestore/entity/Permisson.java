package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table
public class Permisson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int permissonID;

    @Column
    private String name;

    @Column
    private String role;
}

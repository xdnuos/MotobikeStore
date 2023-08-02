package com.example.motobikestore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="manufacturer")
public class Manufacturer implements Serializable {
    @Id
    @Column(name = "manufacturerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int manufacturerID;

    @Column(name = "name", length = 100)
    private String name;

    @Column
    private boolean isActive;

    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Product> product = new ArrayList<>();
}

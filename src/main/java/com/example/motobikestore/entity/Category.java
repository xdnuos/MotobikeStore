package com.example.motobikestore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="category")
public class Category implements Serializable {
    @Id
    @Column(name = "categoryID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int categoryID;

    @Column(name = "name", length = 100)
    private String name;

    @Column
    private boolean isActive;

    @ManyToMany(mappedBy = "categoryList",fetch = FetchType.LAZY)
    @JsonBackReference
    private Collection<Product> productcate = new ArrayList<>();
}

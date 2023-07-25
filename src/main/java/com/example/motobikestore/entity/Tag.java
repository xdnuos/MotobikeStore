package com.example.motobikestore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Table(name="tag")
public class Tag implements Serializable {
    @Id
    @Column(name = "tagID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tagID;

    @Column(name = "name", length = 128)
    private String name;

    @Column
    private Boolean isActive;

    @ManyToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<Product> producttag = new ArrayList<>();
}

package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "variation")
public class Variation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long variationID;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="imageID")
    private Images image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productID")
    private Product product;

    @OneToMany(mappedBy = "variation", cascade = CascadeType.ALL)
    private List<Size> sizes = new ArrayList<>();
}

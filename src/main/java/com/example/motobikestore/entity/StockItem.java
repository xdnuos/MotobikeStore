package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="stockItem")
public class StockItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long stockItemID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productID")
    private Product product;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="stockID")
    private Stock stock;
}

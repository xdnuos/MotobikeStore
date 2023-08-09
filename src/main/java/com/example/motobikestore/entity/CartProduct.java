package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table
public class CartProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cartProductID;

    @Transient
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productID")
    private Product product;

    @Transient
    public BigDecimal getTotalPrice() {
        return this.product.getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }

    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userID")
    private Users users;
}

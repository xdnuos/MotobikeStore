package com.example.motobikestore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name="orderItem")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderItemID;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="productID")
    private Product product;

    @Transient
    public BigDecimal getTotalPrice() {

        return product.getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="orderID")
    private Orders orders;
}

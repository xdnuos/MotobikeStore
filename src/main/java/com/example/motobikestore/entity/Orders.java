package com.example.motobikestore.entity;

import com.example.motobikestore.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderID;

    @Column
    private String fullname;
    @Column
    private String phone;
    @Column
    private String address;
    @Column
    private String note;
    @Column
    private BigDecimal total;
    @Column
    private LocalDateTime orderTime;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="staffID")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="customerID")
    private Customer customer;

    @OneToMany(mappedBy="orders",  cascade=CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

}
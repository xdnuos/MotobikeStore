package com.example.motobikestore.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.example.motobikestore.entity.OrderItem;
import com.example.motobikestore.entity.Orders;
import com.example.motobikestore.enums.OrderStatus;
import com.example.motobikestore.enums.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@EntityView(Orders.class)
public interface OrdersCustomerView {
    @IdMapping
    long getOrderID();

    String getFullname();

    String getPhone();

    String getAddress();

    String getNote();

    BigDecimal getTotal();

    LocalDateTime getOrderTime();

    Payment getPayment();

    OrderStatus getOrderStatus();

    List<OrderItemView> getOrderItems();

    @EntityView(OrderItem.class)
    public interface OrderItemView {
        BigDecimal getPrice();

        int getQuantity();
    }
}
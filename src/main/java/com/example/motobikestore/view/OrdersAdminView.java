package com.example.motobikestore.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.motobikestore.entity.OrderItem;
import com.example.motobikestore.entity.Orders;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.OrderStatus;
import com.example.motobikestore.enums.Payment;
import com.example.motobikestore.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EntityView(Orders.class)
public interface OrdersAdminView {
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

    @Mapping("staff.users")
    UsersView getStaffUsers();

    @Mapping("customer.customerID")
    UUID getCustomerID();

    List<OrderItemView> getOrderItems();

    @EntityView(Users.class)
    public interface UsersView {
        @IdMapping
        UUID getUserID();

        String getFirstName();

        String getLastName();

        boolean isActive();
    }

    @EntityView(OrderItem.class)
    public interface OrderItemView {
        @IdMapping
        long getOrderItemID();

        BigDecimal getPrice();

        ProductView getProduct();

        int getQuantity();

        @EntityView(Product.class)
        public interface ProductView {
            @IdMapping
            Long getProductID();

            String getName();
        }
    }
}
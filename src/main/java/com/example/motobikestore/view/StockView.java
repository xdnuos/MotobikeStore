package com.example.motobikestore.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@EntityView(Stock.class)
public interface StockView {
    @IdMapping
    long getStockID();

    LocalDateTime getCreateDate();

    List<StockItemView> getStockItems();
    Status getStatus();
    @Mapping("staff.staffID")
    UUID getStaffID();

    @Mapping("staff.users")
    UsersView getUsers();

    @EntityView(StockItem.class)
    public interface StockItemView {
        @IdMapping
        long getStockItemID();

        ProductView getProduct();

        int getQuantity();

        @EntityView(Product.class)
        public interface ProductView {
            @IdMapping
            Long getProductID();

            String getSku();

            String getName();

            BigDecimal getPrice();

            Integer getStock();

            Set<ImagesView> getImagesList();

            @EntityView(Images.class)
            public interface ImagesView {
                String getImagePath();
            }
        }
    }

    @EntityView(Users.class)
    public interface UsersView {
        @IdMapping
        UUID getUserID();

        String getFirstName();

        String getLastName();
    }
}
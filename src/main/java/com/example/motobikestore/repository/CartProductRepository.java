package com.example.motobikestore.repository;

import com.example.motobikestore.DTO.CartProductResponse;
import com.example.motobikestore.entity.CartProduct;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    @Query("from CartProduct c where c.product.productID =:id and c.users.email=:email")
    CartProduct findByCartProductIDAndUsers(@Param("id") Long id,@Param("email") String email);

    List<CartProduct> findAllByUsers_UserID(UUID userID);
}
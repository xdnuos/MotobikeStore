package com.example.motobikestore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.example.motobikestore.entity.CartProduct}
 */
@Getter
@Setter
public class CartProductRequest implements Serializable {
    private Long productID;
    private int quantity;
    private String email;
}
package com.example.motobikestore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.example.motobikestore.entity.CartProduct}
 */
@Getter
@Setter
public class CartProductEdit implements Serializable {
    private long cartProductID;
    private int quantity;
    private UUID userID;
}
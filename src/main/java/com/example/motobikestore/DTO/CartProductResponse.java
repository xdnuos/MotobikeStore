package com.example.motobikestore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link com.example.motobikestore.entity.CartProduct}
 */
@Getter
@Setter
public class CartProductResponse implements Serializable {
    private long cartProductID;
    private Long productID;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private Set<ImagesDto> productImages = new HashSet<>();
    private int quantity;
}
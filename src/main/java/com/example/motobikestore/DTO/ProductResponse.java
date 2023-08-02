package com.example.motobikestore.DTO;

import com.example.motobikestore.enums.Arrival;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link com.example.motobikestore.entity.Product}
 */
@AllArgsConstructor
@Getter
public class ProductResponse implements Serializable {
    private final Long productID;
    private final String sku;
    private final String name;
    private final BigDecimal price;
    private final String shortDescription;
    private final String fullDescription;
    private final Arrival arrival;
    private final Float rating;
    private final Integer saleCount;
    private final Integer stock;
    private final String manufacturer;
    private final List<String> categories;
    private final List<String> tags;
    private final List<String> imagePath;
}
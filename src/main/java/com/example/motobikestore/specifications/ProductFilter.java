package com.example.motobikestore.specifications;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductFilter {
    private String manufacturerName;
    private List<String> tagNames = new ArrayList<>();
    private List<String> categoryNames = new ArrayList<>();
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public ProductFilter() {
        super();
    }
    public ProductFilter(String manufacturerName, List<String> tagNamess, List<String> categoryNames, BigDecimal minPrice, BigDecimal maxPrice) {
        this.manufacturerName = manufacturerName;
        this.tagNames = tagNamess;
        this.categoryNames = categoryNames;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
}

package com.example.motobikestore.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.enums.Arrival;

import java.math.BigDecimal;
import java.util.Set;

@EntityView(Product.class)
public interface ProductView {
    @IdMapping
    Long getProductID();

    String getSku();

    String getName();

    BigDecimal getPrice();

    Boolean getActive();

    String getShortDescription();

    String getFullDescription();

    Arrival getArrival();

    Float getRating();

    Integer getSaleCount();

    Integer getStock();

    @Mapping("manufacturer.name")
    String getManufacturer();

    @Mapping("categoryList.name")
    Set<String> getCategories();

    @Mapping("tagList.name")
    Set<String> getTags();

    @Mapping("imagesList.imagePath")
    Set<String> getImages();
}
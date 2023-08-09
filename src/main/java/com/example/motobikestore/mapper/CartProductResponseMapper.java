package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.CartProductResponse;
import com.example.motobikestore.entity.CartProduct;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartProductResponseMapper {
    @Mapping(source = "productImages", target = "product.imagesList")
    @Mapping(source = "productStock", target = "product.stock")
    @Mapping(source = "productPrice", target = "product.price")
    @Mapping(source = "productName", target = "product.name")
    @Mapping(source = "productID", target = "product.productID")
    CartProduct toEntity(CartProductResponse cartProductResponse);

    @AfterMapping
    default void linkImagesList(@MappingTarget CartProduct cartProduct) {
        cartProduct.getProduct().getImagesList().forEach(imagesList -> imagesList.setProduct(cartProduct.getProduct()));
    }

    @InheritInverseConfiguration(name = "toEntity")
    CartProductResponse toDto(CartProduct cartProduct);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartProduct partialUpdate(CartProductResponse cartProductResponse, @MappingTarget CartProduct cartProduct);
}
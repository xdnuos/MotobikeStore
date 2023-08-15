package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.CartProductRequest;
import com.example.motobikestore.entity.CartProduct;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartProductRequestMapper {
    @Mapping(source = "userID", target = "users.userID")
    @Mapping(source = "productID", target = "product.productID")
    CartProduct toEntity(CartProductRequest cartProductRequest);

    @InheritInverseConfiguration(name = "toEntity")
    CartProductRequest toDto(CartProduct cartProduct);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartProduct partialUpdate(CartProductRequest cartProductRequest, @MappingTarget CartProduct cartProduct);
}
package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.product.ProductRequest;
import com.example.motobikestore.entity.Product;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductRequestMapper {
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "saleCount", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "productStock", ignore = true)
    @Mapping(target = "oderItem", ignore = true)
    @Mapping(source = "manufacturerID",target = "manufacturer", ignore = true)
    @Mapping(source = "categoryIDs",target = "categoryList", ignore = true)
    @Mapping(source = "tagIDs",target = "tagList", ignore = true)
    @Mapping(source = "imageFiles",target = "imagesList", ignore = true)
    Product toEntity(ProductRequest productRequest);

    ProductRequest toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductRequest productRequest, @MappingTarget Product product);
}
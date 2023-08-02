package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.entity.Product;
import org.mapstruct.*;

@Mapper
public interface ProductMapper {

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "saleCount", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "productStock", ignore = true)
    @Mapping(target = "oderItem", ignore = true)
    @Mapping(source = "manufacturerID",target = "manufacturer", ignore = true)
    @Mapping(source = "categoryIDs",target = "categoryList", ignore = true)
    @Mapping(source = "tagIDs",target = "tagList", ignore = true)
    @Mapping(source = "imageFiles",target = "imagesList", ignore = true)
    Product toEntity(ProductDTO productDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductDTO productDTO, @MappingTarget Product product);
}
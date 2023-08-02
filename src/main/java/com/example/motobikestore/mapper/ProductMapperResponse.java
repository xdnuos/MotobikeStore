package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.ProductResponse;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.entity.Images;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.entity.Tag;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface ProductMapperResponse {

    @Mapping(target = "categories", expression = "java(categorysToCategoryNames(product.getCategoryList()))")
    @Mapping(target = "tags", expression = "java(tagsToTagNames(product.getTagList()))")
    @Mapping(target = "imagePath", expression = "java(imagesToImageImagePaths(product.getImagesList()))")
    @Mapping(source = "manufacturer.name", target = "manufacturer")
    ProductResponse toDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "manufacturer", target = "manufacturer.name")
    Product partialUpdate(ProductResponse productResponse, @MappingTarget Product product);

    default List<String> categorysToCategoryNames(Set<Category> categorys) {
        return categorys.stream().map(Category::getName).collect(Collectors.toList());
    }

    default List<String> tagsToTagNames(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    default List<String> imagesToImageImagePaths(Set<Images> images) {
        return images.stream().map(Images::getImagePath).collect(Collectors.toList());
    }
}
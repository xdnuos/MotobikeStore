package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CommonMapper commonMapper;
    private final ProductService productService;

    public String addProduct(ProductDTO productDTO, BindingResult bindingResult){
        return "";
    }
}

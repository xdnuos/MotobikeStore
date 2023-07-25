package com.example.motobikestore.controller;

import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.mapper.CommonMapper;
import com.example.motobikestore.repository.blaze.ProductViewRepository;
import com.example.motobikestore.repository.jpa.ProductRepository;
import com.example.motobikestore.service.ProductService;
import com.example.motobikestore.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequestMapping(API_V1_PRODUCT)
public class ProductController {
    @Autowired
    private ProductViewRepository productViewRepository;

    @Autowired
    CommonMapper commonMapper;
    @Autowired
    private ProductService productService;

    @GetMapping(GET)
    public Page<ProductView> getAllProdcutDTO(@KeysetConfig(Product.class) KeysetPageable pageable){
        return productViewRepository.findAllToPage(pageable);
    }

    @PostMapping(ADD)
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Product product = commonMapper.convertToEntity(productDTO, Product.class);
        return ResponseEntity.ok(productService.addProduct(product));
    }
}

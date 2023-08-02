package com.example.motobikestore.controller;

import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.ProductService;
import com.example.motobikestore.specifications.ProductFilter;
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
    private ProductService productService;

//  http://localhost:8080/api/v1/products/get?page=0&size=3&sort=productID,desc
    @GetMapping(GET)
    public Page<ProductView> getAllProductDTO(@KeysetConfig(Product.class) KeysetPageable pageable){
        return productService.findAllToPage(pageable);
    }
    @PostMapping(GET)
    public Page<ProductView> getAllProductWithFiler (@KeysetConfig(Product.class) KeysetPageable pageable,
                                                     @RequestBody(required = false) ProductFilter productFilter){
        return productService.findAllToPageWithFilter(pageable,productFilter);
    }

    @PostMapping(ADD)
    public ResponseEntity<String> addProduct(@ModelAttribute ProductDTO productDTO,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }
}

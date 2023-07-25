package com.example.motobikestore.service;

import com.example.motobikestore.entity.Product;
import com.example.motobikestore.repository.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.motobikestore.constants.SuccessMessage.SUCCESS_ADD_PRODUCT;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public String addProduct(Product product){
        this.productRepository.save(product);
        return SUCCESS_ADD_PRODUCT;
    }
}

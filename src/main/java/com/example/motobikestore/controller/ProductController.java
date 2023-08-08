package com.example.motobikestore.controller;

import com.blazebit.persistence.spring.data.repository.KeysetPageable;
import com.blazebit.persistence.spring.data.webmvc.KeysetConfig;
import com.example.motobikestore.DTO.product.ProductRequest;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.ProductService;
import com.example.motobikestore.specifications.ProductFilter;
import com.example.motobikestore.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequestMapping(API_V1_PRODUCT)
public class ProductController {
    @Autowired
    private ProductService productService;

//  http://localhost:8080/api/v1/products/get?page=0&size=3&sort=productID,desc
    @GetMapping(GET+"pages")
    public Page<ProductView> getAllProductDTO(@KeysetConfig(Product.class) KeysetPageable pageable){
        return productService.findAllToPage(pageable);
    }
//  http://localhost:8080/api/v1/products/get
    @GetMapping(GET)
    public List<ProductView> getProductList(){
        return productService.findAllProduct();
    }
    @GetMapping(GET_BY_ID)
    public ProductView getProductByID(@PathVariable Long id){
        return productService.getProductByID(id);

    }
    @GetMapping("/get/sku/{sku}")
    public ProductView getProductBySku(@PathVariable String sku){
        return productService.getProductBySku(sku);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping("/admin/get/{id}")
    public ProductView getAllProductByID(@PathVariable Long id){
        return productService.getProductByID(id);
    }
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping("/admin/get/sku/{sku}")
    public ProductView getAllProductBySku(@PathVariable String sku){
        return productService.getProductBySku(sku);
    }
    @PostMapping(GET)
    public Page<ProductView> getAllProductWithFiler (@KeysetConfig(Product.class) KeysetPageable pageable,
                                                     @RequestBody(required = false) ProductFilter productFilter){
        return productService.findAllToPageWithFilter(pageable,productFilter);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ADMIN')")
    @PostMapping(ADD)
    public ResponseEntity<String> addProduct(@ModelAttribute ProductRequest productRequest,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(productService.addProduct(productRequest));
    }

    @PutMapping("/changeState/{id}")
    public ResponseEntity<String> changeState(@PathVariable Long id){
        return ResponseEntity.ok(productService.changeProductState(id));
    }

    @PutMapping(EDIT_BY_ID)
    public ResponseEntity<String> editProduct(@ModelAttribute ProductRequest productRequest,@PathVariable Long id,
                                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(productService.editProduct(productRequest,id));
    }
}

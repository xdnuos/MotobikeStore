package com.example.motobikestore.service;

import com.example.motobikestore.DTO.product.ProductRequest;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.ProductRequestMapper;
import com.example.motobikestore.repository.*;
import com.example.motobikestore.repository.blaze.ProductViewRepository;
import com.example.motobikestore.specifications.ProductFilter;
import com.example.motobikestore.specifications.ProductSpecifications;
import com.example.motobikestore.view.ProductView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.motobikestore.constants.ErrorMessage.EMAIL_NOT_FOUND;
import static com.example.motobikestore.constants.ErrorMessage.PRODUCT_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductViewRepository productViewRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ProductRequestMapper productRequestMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ImagesRepository imagesRepository;

    @Transactional
    public String addProduct(ProductRequest productRequest){
        productRepository.findBySku(productRequest.getSku())
                .ifPresent(product -> {
                    throw new ApiRequestException("SKU already exists", HttpStatus.NOT_ACCEPTABLE);
                });
        Product product = productRequestMapper.toEntity(productRequest);
        Set<Images> imagesList = new HashSet<>();
        if(productRequest.getImageFiles() != null) {
            for (MultipartFile file : productRequest.getImageFiles()) {
                Images image = new Images();
                image.setImagePath(imageService.saveImage(file));
                image.setProduct(product);
                imagesList.add(image);
            }
            product.setImagesList(imagesList);
        }
        if (productRequest.getCategoryIDs() != null){
            List<Category> categories = categoryRepository.findAllById(productRequest.getCategoryIDs());
            Set<Category> categorySet = Set.copyOf(categories);
            product.setCategoryList(categorySet);
        }
        if (productRequest.getTagIDs() != null){
            List<Tag> tags = tagRepository.findAllById(productRequest.getTagIDs());
            Set<Tag> tagSet = Set.copyOf(tags);
            product.setTagList(tagSet);
        }
        if(productRequest.getManufacturerID() != null){
            Manufacturer manufacturer = manufacturerRepository.findById(productRequest.getManufacturerID()).get();
            product.setManufacturer(manufacturer);
        }

        product.setActive(true);
        product.setRating(0.0f);
        product.setSaleCount(0);

        productRepository.save(product);
        return SUCCESS_ADD_PRODUCT;
    }
    @Transactional
    public String editProduct(ProductRequest productRequest,Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        Long check_sku = productRepository.findBySku(productRequest.getSku()).get();
        if (!check_sku.equals(id)){
            throw new ApiRequestException("SKU already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        product.setName(productRequest.getName());
        product.setSku(productRequest.getSku());
        product.setStock(productRequest.getStock());
        product.setPrice(productRequest.getPrice());
        product.setShortDescription(productRequest.getShortDescription());
        product.setFullDescription(productRequest.getFullDescription());
        product.setArrival(productRequest.getArrival());

        Set<Images> images = product.getImagesList();
        images.forEach(image -> {
            image.setProduct(null);
        });
        imagesRepository.saveAll(images);
        if(productRequest.getImageFiles() != null) {
            Set<Images> imagesList = new HashSet<>();
            for (MultipartFile file : productRequest.getImageFiles()) {
                Images image = new Images();
                image.setImagePath(imageService.saveImage(file));
                image.setProduct(product);
                imagesList.add(image);
            }
//            product.setImagesList(imagesList);
            imagesRepository.saveAll(imagesList);
        }
        Set<Category> categorySet = new HashSet<>();
        if (!productRequest.getCategoryIDs().isEmpty()){
            List<Category> categories = categoryRepository.findAllById(productRequest.getCategoryIDs());
            categorySet.addAll(categories);
            product.setCategoryList(categorySet);
        }else {
            product.setCategoryList(null);
        }
        Set<Tag> tagSet = new HashSet<>();
        if (productRequest.getTagIDs() != null&!productRequest.getTagIDs().isEmpty()){
            List<Tag> tags = tagRepository.findAllById(productRequest.getTagIDs());
            tagSet.addAll(tags);
            product.setTagList(tagSet);
        }else {
            product.setTagList(null);
        }
        if(productRequest.getManufacturerID() != null){
            Manufacturer manufacturer = manufacturerRepository.findById(productRequest.getManufacturerID()).get();
            product.setManufacturer(manufacturer);
        }else {
            product.setManufacturer(null);
        }

        productRepository.save(product);
        return SUCCESS_UPDATE_PRODUCT;
    }
    public Page<ProductView> findAllToPageWithFilter(Pageable pageable, ProductFilter filters){
        Specification<Product> specification = new ProductSpecifications(filters);
        return productViewRepository.findAllToPageWithFilter(specification,pageable);
    }
    public Page<ProductView> findAllToPage(Pageable pageable){
        return productViewRepository.findAllToPage(pageable);
    }

    public List<ProductView> findAllProduct(){
        Iterable<ProductView> productViews = productViewRepository.findAll();
        return StreamSupport.stream(productViews.spliterator(), false)
                .collect(Collectors.toList());
    }
    public ProductView getProductByID(Long id){
        ProductView productView = productViewRepository.findByProductIDAndActiveIsTrue(id)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return productView;
    }
    public ProductView getProductBySku(String sku){
        ProductView productView = productViewRepository.findBySkuAndActiveIsTrue(sku)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return productView;
    }
    public ProductView getAllProductByID(Long id){
        ProductView productView = productViewRepository.findByProductID(id)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return productView;
    }
    public ProductView getAllProductBySku(String sku){
        ProductView productView = productViewRepository.findBySku(sku)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        return productView;
    }
    @Transactional
    public String changeProductState(Long id){
        boolean isActive = productRepository.getStatusByID(id);
        productRepository.changeStatusByID(id,!isActive);
        return isActive ? SUCCESS_DISABLE_PRODUCT : SUCCESS_ENABLE_PRODUCT;
    }
}

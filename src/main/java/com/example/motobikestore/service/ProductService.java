package com.example.motobikestore.service;

import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.mapper.ProductMapper;
import com.example.motobikestore.mapper.ProductMapperResponse;
import com.example.motobikestore.repository.CategoryRepository;
import com.example.motobikestore.repository.ManufacturerRepository;
import com.example.motobikestore.repository.ProductRepository;
import com.example.motobikestore.repository.TagRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

import static com.example.motobikestore.constants.SuccessMessage.SUCCESS_ADD_PRODUCT;

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
    private ProductMapper productMapper;

    @Autowired
    private ProductMapperResponse productMapperResponse;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Transactional
    public String addProduct(ProductDTO productDTO){
        Product product = productMapper.toEntity(productDTO);
        Set<Images> imagesList = new HashSet<>();
        if(productDTO.getImageFiles() != null) {
            for (MultipartFile file : productDTO.getImageFiles()) {
                Images image = new Images();
                image.setImagePath(imageService.saveImage(file));
                image.setProduct(product);
                imagesList.add(image);
            }
            product.setImagesList(imagesList);
        }
        if (productDTO.getCategoryIDs() != null){
            List<Category> categories = categoryRepository.findAllById(productDTO.getCategoryIDs());
            Set<Category> categorySet = Set.copyOf(categories);
            product.setCategoryList(categorySet);
        }
        if (productDTO.getTagIDs() != null){
            List<Tag> tags = tagRepository.findAllById(productDTO.getTagIDs());
            Set<Tag> tagSet = Set.copyOf(tags);
            product.setTagList(tagSet);
        }
        if(productDTO.getManufacturerID() != null){
            Manufacturer manufacturer = manufacturerRepository.findById(productDTO.getManufacturerID()).get();
            product.setManufacturer(manufacturer);
        }

        product.setActive(true);
        product.setRating(0.0f);
        product.setSaleCount(0);

        productRepository.save(product);
        return SUCCESS_ADD_PRODUCT;
    }

    public Page<ProductView> findAllToPageWithFilter(Pageable pageable, ProductFilter filters){
        Specification<Product> specification = new ProductSpecifications(filters);
        return productViewRepository.findAllToPageWithFilter(specification,pageable);
    }
    public Page<ProductView> findAllToPage(Pageable pageable){
        return productViewRepository.findAllToPage(pageable);
    }
}

package com.example.motobikestore.service;

import com.example.motobikestore.DTO.ProductSearch;
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

import java.time.LocalDateTime;
import java.util.*;
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
    private CloudinaryService cloudinaryService;
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
                image.setImagePath(cloudinaryService.uploadImage(file));
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
        product.setCreateAt(LocalDateTime.now());
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

        Set<Images> currentImages = product.getImagesList();
        Set<Images> imagesToDelete = new HashSet<>();
        Set<Images> imagesToAdd = new HashSet<>();

        for (Images currentImage : currentImages) {
            boolean isImageToRemove = true;
            for (MultipartFile file : productRequest.getImageFiles()) {
                String originalFileName = file.getOriginalFilename();
                String currentFileName = cloudinaryService.extractFileNameFromUrl(currentImage.getImagePath());
                if (currentFileName.equals(originalFileName)) {
                    isImageToRemove = false;
                    break;
                }
            }
            if (isImageToRemove) {
                imagesToDelete.add(currentImage);
            }
        }
        for (MultipartFile file : productRequest.getImageFiles()) {
            boolean isImageToAdd = true;
            for (Images currentImage : currentImages) {
                String originalFileName = file.getOriginalFilename();
                String currentFileName = cloudinaryService.extractFileNameFromUrl(currentImage.getImagePath());

                if (currentFileName.equals(originalFileName)) {
                    isImageToAdd = false;
                    break;
                }
            }
            if (isImageToAdd) {
                Images image = new Images();
                image.setImagePath(cloudinaryService.uploadImage(file));
                image.setProduct(product);
                imagesToAdd.add(image);
            }
        }
        if(imagesToDelete.size()>0){
            System.out.print("Delete Image");
            System.out.println(imagesToDelete);
            imagesToDelete.forEach(images -> {
                images.setProduct(null);
            });
            currentImages.remove(imagesToDelete);
        }
        if(imagesToAdd.size()>0){
            System.out.print("Add Image");
            System.out.println(imagesToDelete);
            imagesRepository.saveAll(imagesToAdd);
            currentImages.addAll(imagesToAdd);
        }
        product.setImagesList(currentImages);
// Kiểm tra xem có sự thay đổi trong categoryIDs hay không
        List<Integer> productCategoryIDs = product.getCategoryList().stream()
                .map(Category::getCategoryID)
                .collect(Collectors.toList());

        List<Integer> newCategoryIDs = productRequest.getCategoryIDs().stream()
                .filter(categoryID -> !productCategoryIDs.contains(categoryID))
                .collect(Collectors.toList());

        if (!newCategoryIDs.isEmpty() || !productRequest.getCategoryIDs().equals(productCategoryIDs)) {
            System.out.println("Có thay đổi category");
            List<Category> newCategories = categoryRepository.findAllById(productRequest.getCategoryIDs());
            product.setCategoryList(new HashSet<>(newCategories));
        }

// Kiểm tra xem có sự thay đổi trong tagIDs hay không
        List<Integer> productTagIDs = product.getTagList().stream()
                .map(Tag::getTagID)
                .collect(Collectors.toList());

        List<Integer> newTagIDs = productRequest.getTagIDs().stream()
                .filter(tagID -> !productTagIDs.contains(tagID))
                .collect(Collectors.toList());

        if (!newTagIDs.isEmpty() || !productRequest.getTagIDs().equals(productTagIDs)) {
            System.out.println("Có thay đổi tag");
            List<Tag> newTags = tagRepository.findAllById(productRequest.getTagIDs());
            product.setTagList(new HashSet<>(newTags));
        }


// Kiểm tra xem có sự thay đổi trong manufacturerID hay không
        if (!Objects.equals(productRequest.getManufacturerID(),
                product.getManufacturer() != null ? product.getManufacturer().getManufacturerID() : null)) {
            System.out.println("Có thay đổi manufacturer");
            if (productRequest.getManufacturerID() != null){
                Manufacturer manufacturer = manufacturerRepository.findById(productRequest.getManufacturerID()).orElse(null);
                product.setManufacturer(manufacturer);
            } else {
                product.setManufacturer(null);
            }
        }

        productRepository.save(product);
        if(imagesToDelete.size()>0) {
            imagesRepository.deleteAll(imagesToDelete);
        }
        return SUCCESS_UPDATE_PRODUCT;
    }
    public Page<ProductView> findAllToPageWithFilter(Pageable pageable, ProductFilter filters){
        Specification<Product> specification = new ProductSpecifications(filters);
        return productViewRepository.findAllToPageWithFilter(specification,pageable);
    }
    public Page<ProductView> findAllToPage(Pageable pageable){
        return productViewRepository.findAllToPage(pageable);
    }

    public List<ProductView> findAllProductActive(){
        List<ProductView> productViews = productViewRepository.findAllByActiveIsTrue();
        return productViews;
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

    public List<ProductView> productSearch(String search){
        return productViewRepository.findAllByActiveIsTrueAndNameIsContainingOrSkuIsContaining(search,search);
//        return null;
    }

    public List<ProductView> findAllWithFilter(ProductFilter filters){
        Specification<Product> specification = new ProductSpecifications(filters);
        return productViewRepository.findAllWithFilter(specification);
    }
}

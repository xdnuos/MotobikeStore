package com.example.motobikestore.repository;

import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.specifications.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT DISTINCT p FROM Product p left JOIN FETCH p.categoryList left JOIN FETCH p.tagList JOIN FETCH p.manufacturer left JOIN FETCH p.imagesList")
    Page<Product> findAll(Pageable pageable);
}

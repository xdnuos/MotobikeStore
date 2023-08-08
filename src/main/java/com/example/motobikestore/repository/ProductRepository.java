package com.example.motobikestore.repository;

import com.example.motobikestore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT DISTINCT p FROM Product p left JOIN FETCH p.categoryList left JOIN FETCH p.tagList JOIN FETCH p.manufacturer left JOIN FETCH p.imagesList")
    Page<Product> findAll(Pageable pageable);

    @Query("select p.productID from Product p where p.sku=:sku")
    Optional<Long> findBySku(@Param("sku") String sku);

    @Modifying
    @Query("UPDATE Product p SET p.active = :status WHERE p.productID = :id")
    void changeStatusByID(@Param("id") Long id,@Param("status") Boolean status);
    @Query("select p.active from Product p where p.productID=:id")
    Boolean getStatusByID(@Param("id") Long id);
}

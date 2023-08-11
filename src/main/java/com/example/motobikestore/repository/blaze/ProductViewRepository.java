package com.example.motobikestore.repository.blaze;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.view.ProductView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface ProductViewRepository extends EntityViewRepository<ProductView, Long> {
    KeysetAwarePage<ProductView> findAllToPage(Pageable pageable);
    KeysetAwarePage<ProductView> findAllToPageWithFilter(Specification<Product> filter, Pageable pageable);
    Optional<ProductView> findByProductID(Long id);

    Optional<ProductView> findByProductIDAndActiveIsTrue(Long id);
    Optional<ProductView> findBySku(String sku);
    Optional<ProductView> findBySkuAndActiveIsTrue(String sku);
    List<ProductView> findAllByActiveIsTrue();
}

package com.example.motobikestore.repository.blaze;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.blazebit.persistence.spring.data.repository.KeysetAwarePage;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.view.ProductView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Repository
public interface ProductViewRepository extends EntityViewRepository<ProductView, Long> {
    KeysetAwarePage<ProductView> findAllToPage(Pageable pageable);
    KeysetAwarePage<ProductView> findAllToPageWithFilter(Specification<Product> filter, Pageable pageable);

}

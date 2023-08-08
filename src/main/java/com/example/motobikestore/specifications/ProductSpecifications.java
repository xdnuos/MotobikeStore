package com.example.motobikestore.specifications;

import com.example.motobikestore.entity.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications implements Specification<Product> {
    ProductFilter productFilter;

    public ProductSpecifications(ProductFilter productFilter) {
        this.productFilter = productFilter;
    }
    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if(productFilter != null) {
            if (productFilter.getManufacturerName() != null && !productFilter.getManufacturerName().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.join("manufacturer").get("name"), productFilter.getManufacturerName()));
            }

            if (!productFilter.getTagNames().isEmpty()) {
                productFilter.getTagNames().forEach((tagName) -> {
                    if (!tagName.isEmpty() || tagName != null) {
                        predicates.add(criteriaBuilder.equal(root.join("tagList").get("name"), tagName));
                    }
                });
            }
            if (!productFilter.getCategoryNames().isEmpty()) {
                productFilter.getCategoryNames().forEach((categoryName) -> {
                    if (!categoryName.isEmpty() || categoryName != null) {
                        predicates.add(criteriaBuilder.equal(root.join("categoryList").get("name"), categoryName));
                    }
                });
            }

            if (productFilter.getMinPrice() != null && productFilter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.between(root.get("price"), productFilter.getMinPrice(), productFilter.getMaxPrice()));
            } else if (productFilter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productFilter.getMinPrice()));
            } else if (productFilter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), productFilter.getMaxPrice()));
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

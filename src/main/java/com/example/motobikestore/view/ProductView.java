package com.example.motobikestore.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.enums.Arrival;

import java.math.BigDecimal;
import java.util.List;

@EntityView(Product.class)
public interface ProductView {
    @IdMapping
    Long getProductID();

    String getSku();

    String getName();

    BigDecimal getPrice();

    String getShortDescription();

    String getFullDescription();

    Arrival getArrival();

    float getRating();

    int getSaleCount();

    ManufacturerView getManufacturer();

    List<CategoryView> getCategorys();

    List<TagView> getTags();

    List<ImagesView> getImages();
    @Mapping(fetch = FetchStrategy.MULTISET)
    List<VariationView> getVariations();

    @EntityView(Manufacturer.class)
    public interface ManufacturerView {
        String getName();
    }

    @EntityView(Category.class)
    public interface CategoryView {
        String getName();
    }

    @EntityView(Tag.class)
    public interface TagView {
        String getName();
    }

    @EntityView(Images.class)
    public interface ImagesView {
        String getImagePath();
    }

    @EntityView(Variation.class)
    public interface VariationView {
        String getName();

        ImagesView getImage();
        @Mapping(fetch = FetchStrategy.MULTISET)
        List<SizeView> getSizes();

        @EntityView(Images.class)
        public interface ImagesView {
            String getImagePath();
        }

        @EntityView(Size.class)
        public interface SizeView {
            String getName();

            int getStock();
        }
    }
}
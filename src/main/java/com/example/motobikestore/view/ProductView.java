package com.example.motobikestore.view;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
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

    String getFullDescription();

    Arrival getArrival();

    float getRating();

    int getSaleCount();

    ManufacturerView getManufacturer();

    List<CategoryView> getCategory();

    List<TagView> getTag();

    List<ImagesView> getImages();

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
package com.example.motobikestore.DTO;

import com.example.motobikestore.enums.Arrival;
import com.example.motobikestore.service.ImageService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO implements Serializable {
    ImageService imageService;

    private String sku;
    private String name;
    private BigDecimal price;
    private String shortDescription;
    private  String fullDescription;
    private Arrival arrival;
    private List<Integer> categoryIds;
    private List<Integer> tagIds;
    private Integer manufacturerIds;
    private List<String> imagePath;
    private List<VarientDTO> varientDTOS;

    public ProductDTO(String sku, String name, BigDecimal price, String shortDescription, String fullDescription, Arrival arrival, List<Integer> categoryIds, List<Integer> tagIds, MultipartFile[] images, List<VarientDTO> varientDTOS) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.arrival = arrival;
        this.categoryIds = categoryIds;
        this.tagIds = tagIds;
        this.imagePath = imageService.saveImages(images);
        this.varientDTOS = varientDTOS;
    }
}

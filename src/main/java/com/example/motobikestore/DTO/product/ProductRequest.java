package com.example.motobikestore.DTO.product;

import com.example.motobikestore.enums.Arrival;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for {@link com.example.motobikestore.entity.Product}
 */
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest implements Serializable {
    private final Long productID;
    @Size(message = "Độ dài SKU từ 2-8 kí tự", min = 2, max = 8)
    @NotEmpty(message = "Vui lòng nhập mã SKU")
    private final String sku;
    @Size(message = "Độ dài tên sản phẩm từ 4-128 kí tự", min = 4, max = 128)
    @NotEmpty(message = "Vui lòng nhập tên sản phẩm")
    private final String name;
    @Min(message = "Giá sản phẩm không hợp lệ", value = 1)
    private final BigDecimal price;
    @Size(message = "Độ dài mô tả từ 20-256 kí tự ", min = 20, max = 512)
    private final String shortDescription;
    @Size(message = "Độ dài mô tả từ 20-4086 kí tự ", min = 20, max = 4086)
    private final String fullDescription;
    private final Arrival arrival;
    private final Integer stock;
    private final List<Integer> categoryIDs;
    private final List<Integer> tagIDs;
    private final Integer manufacturerID;
    private final List<MultipartFile> imageFiles;
}
package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Cấu hình trước mỗi test nếu cần thiết
    }

    @Test
    public void testAddProduct_ValidInput_ReturnOk() throws Exception {
        // Dữ liệu đầu vào để kiểm thử (thay thế bằng dữ liệu thích hợp)
        ProductDTO productDTO = new ProductDTO();
        productDTO.setSku("SKU123");
        productDTO.setName("Product Name");
        productDTO.setPrice(BigDecimal.valueOf(100));
        // ...

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("productDTO", productDTO))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("someExpectedValue"));

        // Kiểm tra kết quả của phương thức addProduct, ví dụ: kiểm tra dữ liệu đã được lưu vào database đúng chưa
    }

    @Test
    public void testAddProduct_InvalidInput_ReturnBadRequest() throws Exception {
        // Dữ liệu đầu vào không hợp lệ để kiểm thử (thay thế bằng dữ liệu thích hợp)
        ProductDTO productDTO = new ProductDTO();
        // ...

        mockMvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .flashAttr("productDTO", productDTO))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Kiểm tra kết quả của phương thức addProduct khi có lỗi đầu vào, ví dụ: kiểm tra lỗi đã được xử lý đúng chưa
    }
}

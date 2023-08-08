//package com.example.motobikestore.controller;
//
//import com.example.motobikestore.DTO.product.ProductRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.math.BigDecimal;
//
//@WebMvcTest
//public class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setUp() {
//        // Cấu hình trước mỗi test nếu cần thiết
//    }
//
//    @Test
//    public void testAddProduct_ValidInput_ReturnOk() throws Exception {
//        // Dữ liệu đầu vào để kiểm thử (thay thế bằng dữ liệu thích hợp)
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setSku("SKU123");
//        productRequest.setName("Product Name");
//        productRequest.setPrice(BigDecimal.valueOf(100));
//        // ...
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/add")
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .flashAttr("productDTO", productRequest))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("someExpectedValue"));
//
//        // Kiểm tra kết quả của phương thức addProduct, ví dụ: kiểm tra dữ liệu đã được lưu vào database đúng chưa
//    }
//
//    @Test
//    public void testAddProduct_InvalidInput_ReturnBadRequest() throws Exception {
//        // Dữ liệu đầu vào không hợp lệ để kiểm thử (thay thế bằng dữ liệu thích hợp)
//        ProductRequest productRequest = new ProductRequest();
//        // ...
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/add")
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
//                        .flashAttr("productDTO", productRequest))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//
//        // Kiểm tra kết quả của phương thức addProduct khi có lỗi đầu vào, ví dụ: kiểm tra lỗi đã được xử lý đúng chưa
//    }
//}

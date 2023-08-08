//package com.example.motobikestore.service;
//
//import com.example.motobikestore.DTO.product.ProductRequest;
//import com.example.motobikestore.mapper.CommonMapper;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
////@RunWith(SpringRunner.class)
//public class ProductServiceTest {
//    @Mock
//    private ProductService productService;
//
//    @MockBean
//    private CommonMapper commonMapper;
//
//    @Test
//    public void testSaveProduct() {
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setName("name");
//        productRequest.setSku("sku");
//        productRequest.setPrice(BigDecimal.valueOf(500));
//        productRequest.setShortDescription("setShortDescriptionsetShortDescription");
//        productRequest.setShortDescription("setShortDescriptionsetShortDescription");
//        List<Integer> integers= new ArrayList<>();
//        integers.add(0);
//        productRequest.setCategoryIds(integers);
//
//        String result = productService.addProduct(productRequest);
//
//        assertEquals("Product successfully adder", result);
//    }
//}

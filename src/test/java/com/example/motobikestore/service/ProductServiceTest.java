package com.example.motobikestore.service;

import com.example.motobikestore.DTO.ProductDTO;
import com.example.motobikestore.mapper.CommonMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@RunWith(SpringRunner.class)
public class ProductServiceTest {
    @Mock
    private ProductService productService;

    @MockBean
    private CommonMapper commonMapper;

    @Test
    public void testSaveProduct() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("name");
        productDTO.setSku("sku");
        productDTO.setPrice(BigDecimal.valueOf(500));
        productDTO.setShortDescription("setShortDescriptionsetShortDescription");
        productDTO.setShortDescription("setShortDescriptionsetShortDescription");
        List<Integer> integers= new ArrayList<>();
        integers.add(0);
        productDTO.setCategoryIds(integers);

        String result = productService.addProduct(productDTO);

        assertEquals("Product successfully adder", result);
    }
}

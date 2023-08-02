package com.example.motobikestore.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class ImageServiceTest {
    private ImageService imageService;

    @Before
    public void setUp() {
        imageService = new ImageService();
    }

    @Test
    public void testSaveImages() {
        MockMultipartFile file1 = new MockMultipartFile("file", "image1.jpg", "image/jpeg", "test data".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "image2.jpg", "image/jpeg", "test data".getBytes());

        List<String> result = imageService.saveImages(new MultipartFile[]{file1, file2});

        assertEquals(2, result.size());
        assertEquals("localhost:5000//image/image1.jpg", result.get(0));
        assertEquals("localhost:5000//image/image2.jpg", result.get(1));
    }
}

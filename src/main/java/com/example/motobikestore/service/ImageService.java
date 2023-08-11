package com.example.motobikestore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.example.motobikestore.constants.PathConstants.SAVEIMAGEPATH;

@Service
public class ImageService {
    public List<String> saveImages(MultipartFile[] files){
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            images.add(transferFile(file));
        }
        return images;
    }
    private String transferFile(MultipartFile file){
        String randomNumber = ActivationCodeGenerator.generateNumberCode();
        String filename = randomNumber+file.getOriginalFilename();
        System.out.print("save image: ");
        System.out.println(filename);
        try {
            byte[] bytes = file.getBytes();
            String path = SAVEIMAGEPATH + filename;
            Files.write(Path.of(path), bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public String saveImage(MultipartFile file){
        return transferFile(file);
    }
}

package com.example.motobikestore.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.motobikestore.constants.PathConstants.IMAGEURL;
import static com.example.motobikestore.constants.PathConstants.SAVEIMAGEPATH;

@Service
public class ImageService {
    public List<String> saveImages(MultipartFile[] files){
        List<String> images = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String path = SAVEIMAGEPATH + file.getOriginalFilename();
                file.transferTo(new File(path));
                images.add(IMAGEURL+path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    public String saveImage(MultipartFile file){
        String path = SAVEIMAGEPATH + file.getOriginalFilename();
        try {
            file.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return IMAGEURL+path;
    }
}

package com.example.motobikestore.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {
    private String imagePath;

    public ImageDTO(String imagePath) {
        this.imagePath = imagePath;
    }
}

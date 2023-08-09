package com.example.motobikestore.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link com.example.motobikestore.entity.Images}
 */
@Getter
@Setter
public class ImagesDto implements Serializable {
    private String imagePath;
}
package com.example.motobikestore.DTO;

import com.example.motobikestore.service.ImageService;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import static com.example.motobikestore.constants.ErrorMessage.EMPTY_COLOR;
import static com.example.motobikestore.constants.ErrorMessage.EMPTY_SIZE;
@Getter
@Setter
@NoArgsConstructor
public class VarientDTO {
    ImageService imageService;
    @NotNull(message = EMPTY_COLOR)
    private String name;

    private String imagePath;

    @NotNull(message = EMPTY_SIZE)
    private SizeDTO sizeDTO;

    public VarientDTO(String name, MultipartFile image, SizeDTO sizeDTO) {
        this.name = name;
        this.imagePath = imageService.saveImage(image);
        this.sizeDTO = sizeDTO;
    }
}

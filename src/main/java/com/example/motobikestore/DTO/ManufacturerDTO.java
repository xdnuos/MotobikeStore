package com.example.motobikestore.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.motobikestore.constants.ErrorMessage.EMPTY_CONTENT;

@Getter
@Setter
@NoArgsConstructor
public class ManufacturerDTO {
    private int manufacturerID;
    @NotBlank(message = EMPTY_CONTENT)
    private String name;

    public ManufacturerDTO(int manufacturerID, String name) {
        this.manufacturerID = manufacturerID;
        this.name = name;
    }
}

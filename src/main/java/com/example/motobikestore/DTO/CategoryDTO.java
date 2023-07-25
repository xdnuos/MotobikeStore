package com.example.motobikestore.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.example.motobikestore.constants.ErrorMessage.EMPTY_CONTENT;
import static com.example.motobikestore.constants.ErrorMessage.PASSWORD_CHARACTER_LENGTH;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private int categoryID;
    @NotBlank(message = EMPTY_CONTENT)
    private String name;

    public CategoryDTO(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }
}

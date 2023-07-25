package com.example.motobikestore.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.motobikestore.constants.ErrorMessage.EMPTY_CONTENT;
import static com.example.motobikestore.constants.ErrorMessage.PASSWORD_CHARACTER_LENGTH;

@Getter
@Setter
@NoArgsConstructor
public class TagDTO {
    private int tagID;
    @NotBlank(message = EMPTY_CONTENT)
    private String name;

    public TagDTO(int tagID, String name) {
        this.tagID = tagID;
        this.name = name;
    }
}

package com.example.motobikestore.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link com.example.motobikestore.entity.Category}
 */
@AllArgsConstructor
@Getter
public class CategoryDTO implements Serializable {
    private final int categoryID;
    private final String name;
}
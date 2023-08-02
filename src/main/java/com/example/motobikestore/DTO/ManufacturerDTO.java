package com.example.motobikestore.DTO;

import com.example.motobikestore.entity.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link Manufacturer}
 */
@AllArgsConstructor
@Getter
public class ManufacturerDTO implements Serializable {
    private final int manufacturerID;
    private final String name;
}
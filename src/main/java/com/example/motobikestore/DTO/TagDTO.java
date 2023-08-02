package com.example.motobikestore.DTO;

import com.example.motobikestore.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * DTO for {@link Tag}
 */
@AllArgsConstructor
@Getter
public class TagDTO implements Serializable {
    private final int tagID;
    private final String name;
}
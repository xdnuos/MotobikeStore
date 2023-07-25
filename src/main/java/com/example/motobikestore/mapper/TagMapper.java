package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.DTO.TagDTO;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.entity.Tag;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.CategoryService;
import com.example.motobikestore.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class TagMapper {
    private final CommonMapper commonMapper;
    private final TagService tagService;

    public String addTag(TagDTO tagDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Tag tag = commonMapper.convertToEntity(tagDTO, Tag.class);
        tag.setIsActive(true);
        return tagService.addTag(tag);
    }
}
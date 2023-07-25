package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final CommonMapper commonMapper;
    private final CategoryService categoryService;

    public String addCategory(CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Category category = commonMapper.convertToEntity(categoryDTO, Category.class);
        category.setActive(true);
        return categoryService.addCategory(category);
    }

}

package com.example.motobikestore.service;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.CategoryMapper;
import com.example.motobikestore.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.motobikestore.constants.ErrorMessage.CATEGORY_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    public Set<CategoryDTO> findAllDTO() {
        // TODO Auto-generated method stub
        return this.categoryRepository.findAllNew();
    }

    public CategoryDTO findByIdDTO(int id){
        return this.categoryRepository.findByIdDTO(id).orElseThrow(() -> new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Category findById(int id){
        return this.categoryRepository.findById(id).orElseThrow(() -> new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Set<CategoryDTO> findAllActiveDTO(){
        return this.categoryRepository.findAllActive();
    }

    public CategoryDTO findAllActiveByIdDTO(int id){
        return this.categoryRepository.findAllActiveById(id).orElseThrow(() -> new ApiRequestException(CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public String addCategory(CategoryDTO categoryDTO){
        if (!categoryRepository.existsByName(categoryDTO.getName())){
            Category category = categoryMapper.toEntity(categoryDTO);
            category.setActive(true);
            this.categoryRepository.save(category);
            return SUCCESS_ADD_CATE;
        }
        return EXIST_CATE;
    }
    @Transactional
    public String updateCategory(Category category){
        if (!categoryRepository.existsByName(category.getName())){
            this.categoryRepository.save(category);
            return SUCCESS_UPDATE_CATE;
        }
        return EXIST_CATE;
    }
    @Transactional
    public String changeStateCategory(int id){
        Category category = findById(id);
        boolean isActive = category.isActive();
        category.setActive(!isActive);
        this.categoryRepository.save(category);
        return isActive ? SUCCESS_DISABLE_CATE : SUCCESS_ENABLE_CATE;
    }
}

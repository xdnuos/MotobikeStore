package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_CATEGORIES)
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping(GET)
    public Set<CategoryDTO> getAllCategoriesActive() {
        return categoryService.findAllActiveDTO();
    }

    @GetMapping(GET_BY_ID)
    public CategoryDTO getCategoriesById(@PathVariable int id) {
        return categoryService.findByIdDTO(id);
    }

    @GetMapping(GET_ALL)
    public Set<CategoryDTO> getAllCategories() {
        return categoryService.findAllDTO();
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping(value = ADD)
    @PostMapping(value = ADD)
    public ResponseEntity<String>
    addCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(categoryService.addCategory(categoryDTO));
    }

    @PutMapping(value = EDIT_BY_ID)
    public ResponseEntity<String> editCategory(@PathVariable int id,@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Category targetCategory = categoryService.findById(id);
        targetCategory.setName(categoryDTO.getName());
        return ResponseEntity.ok(categoryService.updateCategory(targetCategory));
    }

    @PutMapping(value = UPDATE_STATE_BY_ID)
    public ResponseEntity<String> updateState(@PathVariable int id){
        return ResponseEntity.ok(categoryService.changeStateCategory(id));
    }
}

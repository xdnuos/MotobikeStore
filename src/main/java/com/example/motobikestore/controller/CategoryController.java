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

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String,Object>> addCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        String message = categoryService.addCategory(categoryDTO);
        return map(message);
    }
    @PutMapping(value = EDIT)
    public ResponseEntity<Map<String,Object>> editCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        String message = categoryService.updateCategory(categoryDTO);
        return map(message);
    }
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Map<String,Object>> deleteCategory(@PathVariable Integer id){
        String message = categoryService.deleteCategory(id);
        return map(message);
    }

    private ResponseEntity<Map<String,Object>> map(String message){
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        Set<CategoryDTO> categoryDTOS = getAllCategories();
        map.put("categories",categoryDTOS);
        return ResponseEntity.ok(map);
    }
    @PutMapping(value = UPDATE_STATE_BY_ID)
    public ResponseEntity<String> updateState(@PathVariable int id){
        return ResponseEntity.ok(categoryService.changeStateCategory(id));
    }
}

package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.DTO.TagDTO;
import com.example.motobikestore.entity.Tag;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.TagService;
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
@RequestMapping(API_V1_TAG)
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping(GET)
    public Set<TagDTO> getAllTagActive() {
        return tagService.findAllActiveDTO();
    }

    @GetMapping(GET_BY_ID)
    public TagDTO getTagById(@PathVariable int id) {
        return tagService.findByIdDTO(id);
    }

    @GetMapping(GET_ALL)
    public Set<TagDTO> getAllTag() {
        return tagService.findAllDTO();
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = ADD)
    public ResponseEntity<Map<String,Object>> add(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        String message = tagService.addTag(tagDTO);
        return map(message);
    }

    @PutMapping(value = EDIT)
    public ResponseEntity<Map<String,Object>> edit(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        String message = tagService.updateTag(tagDTO);
        return map(message);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Map<String,Object>> deleteTag(@PathVariable Integer id){
        String message = tagService.deleteTag(id);
        return map(message);
    }
    @PutMapping(value = UPDATE_STATE_BY_ID)
    public ResponseEntity<String> updateState(@PathVariable int id){
        return ResponseEntity.ok(tagService.changeState(id));
    }

    private ResponseEntity<Map<String,Object>> map(String message){
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        Set<TagDTO> tagDTOS = getAllTag();
        map.put("tags",tagDTOS);
        return ResponseEntity.ok(map);
    }


}

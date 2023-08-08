package com.example.motobikestore.controller;

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
    public ResponseEntity<String> add(@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(tagService.addTag(tagDTO));
    }

    @PutMapping(value = EDIT_BY_ID)
    public ResponseEntity<String> edit(@PathVariable int id,@Valid @RequestBody TagDTO tagDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Tag targetTag = tagService.findById(id);
        targetTag.setName(tagDTO.getName());
        return ResponseEntity.ok(tagService.updateTag(targetTag));
    }

    @PutMapping(value = UPDATE_STATE_BY_ID)
    public ResponseEntity<String> updateState(@PathVariable int id){
        return ResponseEntity.ok(tagService.changeState(id));
    }
}

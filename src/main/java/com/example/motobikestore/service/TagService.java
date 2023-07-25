package com.example.motobikestore.service;

import com.example.motobikestore.DTO.TagDTO;
import com.example.motobikestore.entity.Tag;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.repository.jpa.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.motobikestore.constants.ErrorMessage.TAG_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<TagDTO> findAllDTO() {
        // TODO Auto-generated method stub
        return this.tagRepository.findAllNew();
    }

    public TagDTO findByIdDTO(int id){
        return this.tagRepository.findByIdDTO(id).orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
    public Tag findById(int id){
        return this.tagRepository.findById(id).orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public List<TagDTO> findAllActiveDTO(){
        return this.tagRepository.findAllActive();
    }

    public TagDTO findAllActiveByIdDTO(int id){
        return this.tagRepository.findAllActiveById(id).orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public String addTag(Tag tag){
        this.tagRepository.save(tag);
        return "Tag successfully adder.";
    }

    @Transactional
    public String updateTag(Tag tag){
        this.tagRepository.save(tag);
        return SUCCESS_UPDATE_TAG;
    }

    @Transactional
    public String changeState(int id){
        Tag tag = findById(id);
        boolean isActive = tag.getIsActive();
        tag.setIsActive(!isActive);
        this.tagRepository.save(tag);
        return isActive ? SUCCESS_DISABLE_TAG : SUCCESS_ENABLE_TAG;
    }
}

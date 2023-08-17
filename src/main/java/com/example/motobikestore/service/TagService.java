package com.example.motobikestore.service;

import com.example.motobikestore.DTO.TagDTO;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.entity.Tag;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.TagMapper;
import com.example.motobikestore.repository.ProductRepository;
import com.example.motobikestore.repository.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.motobikestore.constants.ErrorMessage.CATEGORY_NOT_FOUND;
import static com.example.motobikestore.constants.ErrorMessage.TAG_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ProductRepository productRepository;
    public Set<TagDTO> findAllDTO() {
        // TODO Auto-generated method stub
        return this.tagRepository.findAllDTO();
    }

    public TagDTO findByIdDTO(int id){
        return this.tagRepository.findByIdDTO(id).orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
    public Tag findById(int id){
        return this.tagRepository.findById(id).orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
    private void exitsTagByName(String name){
        if (tagRepository.existsByName(name)){
            throw new ApiRequestException(EXIST_TAG, HttpStatus.NOT_FOUND);
        }
    }
    public Set<TagDTO> findAllActiveDTO(){
        return this.tagRepository.findAllActive();
    }

    public TagDTO findAllActiveByIdDTO(int id){
        return this.tagRepository.findAllActiveById(id).orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public String addTag(TagDTO tagDTO){
        exitsTagByName(tagDTO.getName());
        Tag tag = tagMapper.toEntity(tagDTO);
        tag.setIsActive(true);
        this.tagRepository.save(tag);
        return SUCCESS_ADD_TAG;
    }

    @Transactional
    public String updateTag(TagDTO tagDTO){
        exitsTagByName(tagDTO.getName());
        Tag tag = tagRepository.findById(tagDTO.getTagID())
                .orElseThrow(() -> new ApiRequestException(TAG_NOT_FOUND, HttpStatus.NOT_FOUND));
        tag.setName(tagDTO.getName());
        tagRepository.save(tag);
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

    @Transactional
    public String deleteTag(Integer id){
        Tag tag = findById(id);
        if(productRepository.existsByTagListContaining(tag)){
            throw new ApiRequestException("Can't delete this tag", HttpStatus.FORBIDDEN);
        }
        tagRepository.delete(tag);
        return SUCCESS_DELETE_TAG;
    }
}

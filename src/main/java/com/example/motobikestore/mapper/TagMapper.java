package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.TagDTO;
import com.example.motobikestore.entity.Tag;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TagMapper {
    Tag toEntity(TagDTO tagDTO);

    TagDTO toDto(Tag tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag partialUpdate(TagDTO tagDTO, @MappingTarget Tag tag);
}
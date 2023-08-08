package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Manufacturer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ManufacturerMapper {
    Manufacturer toEntity(ManufacturerDTO manufacturerDTO);

    ManufacturerDTO toDto(Manufacturer manufacturer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Manufacturer partialUpdate(ManufacturerDTO manufacturerDTO, @MappingTarget Manufacturer manufacturer);
}
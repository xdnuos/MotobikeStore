package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.AddressDTO;
import com.example.motobikestore.entity.Address;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address toEntity(AddressDTO addressDTO);

    AddressDTO toDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(AddressDTO addressDTO, @MappingTarget Address address);
}
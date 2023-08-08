package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.StaffRequest;
import com.example.motobikestore.entity.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffRequestMapper {
    Staff toEntity(StaffRequest staffRequest);

    StaffRequest toDto(Staff staff);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffRequest staffRequest, @MappingTarget Staff staff);
}
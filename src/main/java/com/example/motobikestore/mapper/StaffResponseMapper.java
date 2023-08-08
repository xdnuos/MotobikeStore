package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.StaffResponse;
import com.example.motobikestore.entity.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffResponseMapper {
    @Mapping(source = "managerUsersLastName", target = "users.lastName")
    @Mapping(source = "managerUsersUserID", target = "users.userID")
    Staff toEntity(StaffResponse staffResponse);

    @InheritInverseConfiguration(name = "toEntity")
    StaffResponse toDto(Staff staff);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffResponse staffResponse, @MappingTarget Staff staff);
}
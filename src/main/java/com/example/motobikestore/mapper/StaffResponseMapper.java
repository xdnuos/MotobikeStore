package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.StaffResponse;
import com.example.motobikestore.entity.Staff;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StaffResponseMapper {
    @Mapping(source = "managerID", target = "manager.users.userID")
    @Mapping(source = "managerLastName", target = "manager.users.lastName")
    @Mapping(source = "firstName", target = "users.firstName")
    @Mapping(source = "lastName", target = "users.lastName")
    @Mapping(source = "email", target = "users.email")
    @Mapping(source = "roles", target = "users.roles")
    @Mapping(source = "userID", target = "users.userID")
    @Mapping(source = "isActive", target = "users.active")
    @Mapping(source = "createDate", target = "users.createDate")
    Staff toEntity(StaffResponse staffResponse);

    @InheritInverseConfiguration(name = "toEntity")
    StaffResponse toDto(Staff staff);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Staff partialUpdate(StaffResponse staffResponse, @MappingTarget Staff staff);
}
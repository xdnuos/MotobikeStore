package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.UsersResponse;
import com.example.motobikestore.entity.Users;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsersResponseMapper {
    Users toEntity(UsersResponse usersResponse);

    UsersResponse toDto(Users users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users partialUpdate(UsersResponse usersResponse, @MappingTarget Users users);
}
package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.UsersRequest;
import com.example.motobikestore.entity.Users;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsersRequestMapper {
    Users toEntity(UsersRequest usersRequest);

    UsersRequest toDto(Users users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Users partialUpdate(UsersRequest usersRequest, @MappingTarget Users users);
}
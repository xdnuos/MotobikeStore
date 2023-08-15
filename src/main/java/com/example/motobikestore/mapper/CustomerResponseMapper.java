package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.CustomerResponse;
import com.example.motobikestore.entity.Customer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerResponseMapper {

    @Mapping(source = "avatarUrl", target = "users.avatar.imagePath")
    Customer toEntity(CustomerResponse customerResponse);
    @InheritInverseConfiguration(name = "toEntity")
    CustomerResponse toDto(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerResponse customerResponse, @MappingTarget Customer customer);
}
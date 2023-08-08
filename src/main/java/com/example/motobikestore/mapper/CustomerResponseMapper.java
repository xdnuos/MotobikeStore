package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.CustomerResponse;
import com.example.motobikestore.entity.Customer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerResponseMapper {
    Customer toEntity(CustomerResponse customerResponse);

    CustomerResponse toDto(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerResponse customerResponse, @MappingTarget Customer customer);
}
package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.CustomerRequest;
import com.example.motobikestore.entity.Customer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerRequestMapper {
    Customer toEntity(CustomerRequest customerRequest);

    CustomerRequest toDto(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerRequest customerRequest, @MappingTarget Customer customer);
}
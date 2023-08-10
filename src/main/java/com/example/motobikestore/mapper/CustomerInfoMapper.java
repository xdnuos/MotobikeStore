package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.CustomerInfo;
import com.example.motobikestore.entity.Customer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerInfoMapper {
    @Mapping(source = "lastName", target = "users.lastName")
    @Mapping(source = "firstName", target = "users.firstName")
    Customer toEntity(CustomerInfo customerInfo);

    @InheritInverseConfiguration(name = "toEntity")
    CustomerInfo toDto(Customer customer);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerInfo customerInfo, @MappingTarget Customer customer);
}
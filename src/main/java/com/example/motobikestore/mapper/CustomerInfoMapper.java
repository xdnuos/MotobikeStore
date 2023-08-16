package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.user.CustomerBasicInfo;
import com.example.motobikestore.entity.Customer;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerInfoMapper {
    @Mapping(source = "lastName", target = "users.lastName")
    @Mapping(source = "firstName", target = "users.firstName")
    Customer toEntity(CustomerBasicInfo customerBasicInfo);

    @InheritInverseConfiguration(name = "toEntity")
    CustomerBasicInfo toDto(Customer customer);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerBasicInfo customerBasicInfo, @MappingTarget Customer customer);
}
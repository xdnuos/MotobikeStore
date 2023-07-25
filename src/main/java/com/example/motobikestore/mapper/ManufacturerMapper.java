package com.example.motobikestore.mapper;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Category;
import com.example.motobikestore.entity.Manufacturer;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class ManufacturerMapper {
    private final CommonMapper commonMapper;
    private final ManufacturerService manufacturerService;

    public String addManufacturer(ManufacturerDTO manufacturerDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Manufacturer manufacturer = commonMapper.convertToEntity(manufacturerDTO, Manufacturer.class);
        manufacturer.setActive(true);
        return manufacturerService.addManufacturer(manufacturer);
    }
}

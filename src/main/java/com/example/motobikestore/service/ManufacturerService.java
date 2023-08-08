package com.example.motobikestore.service;

import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Manufacturer;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.ManufacturerMapper;
import com.example.motobikestore.repository.ManufacturerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.motobikestore.constants.ErrorMessage.MANUFACTURER_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    @Autowired
    private ManufacturerMapper manufacturerMapper;

    public Set<ManufacturerDTO> findAll() {
        // TODO Auto-generated method stub
        return this.manufacturerRepository.findAllNew();
    }

    public ManufacturerDTO findByIdDTO(int id){
        return this.manufacturerRepository.findByIdDTO(id).orElseThrow(() -> new ApiRequestException(MANUFACTURER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Manufacturer findById(int id){
        return this.manufacturerRepository.findById(id).orElseThrow(() -> new ApiRequestException(MANUFACTURER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Set<ManufacturerDTO> findAllActiveDTO(){
        return this.manufacturerRepository.findAllActive();
    }

    public ManufacturerDTO findAllActiveByIdDTO(int id){
        return this.manufacturerRepository.findAllActiveById(id).orElseThrow(() -> new ApiRequestException(MANUFACTURER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public String addManufacturer(ManufacturerDTO manufacturerDTO){
        if (!manufacturerRepository.existsByName(manufacturerDTO.getName())){
            Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerDTO);
            manufacturer.setActive(true);
            manufacturerRepository.save(manufacturer);
            return SUCCESS_ADD_MANU;
        }
        return EXIST_MANU;
    }

    @Transactional
    public String updateManufacture(Manufacturer manufacturer){
        if (!manufacturerRepository.existsByName(manufacturer.getName())) {
            this.manufacturerRepository.save(manufacturer);
            return SUCCESS_UPDATE_MANU;
        }
        return EXIST_MANU;
    }

    @Transactional
    public String changeState(int id){
        Manufacturer manufacturer = findById(id);
        boolean isActive = manufacturer.isActive();
        manufacturer.setActive(!isActive);
        this.manufacturerRepository.save(manufacturer);
        return isActive ? SUCCESS_DISABLE_MANU : SUCCESS_ENABLE_MANU;
    }
}

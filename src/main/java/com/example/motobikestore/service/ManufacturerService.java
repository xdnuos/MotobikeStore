package com.example.motobikestore.service;

import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Manufacturer;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.repository.jpa.ManufacturerRespository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.motobikestore.constants.ErrorMessage.MANUFACTURER_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.*;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRespository manufacturerRespository;

    public List<ManufacturerDTO> findAll() {
        // TODO Auto-generated method stub
        return this.manufacturerRespository.findAllNew();
    }

    public ManufacturerDTO findByIdDTO(int id){
        return this.manufacturerRespository.findByIdDTO(id).orElseThrow(() -> new ApiRequestException(MANUFACTURER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public Manufacturer findById(int id){
        return this.manufacturerRespository.findById(id).orElseThrow(() -> new ApiRequestException(MANUFACTURER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public List<ManufacturerDTO> findAllActiveDTO(){
        return this.manufacturerRespository.findAllActive();
    }

    public ManufacturerDTO findAllActiveByIdDTO(int id){
        return this.manufacturerRespository.findAllActiveById(id).orElseThrow(() -> new ApiRequestException(MANUFACTURER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public String addManufacturer(Manufacturer manufacturer){
        this.manufacturerRespository.save(manufacturer);
        return "Manufacturer successfully adder.";
    }

    @Transactional
    public String updateManufacture(Manufacturer manufacturer){
        this.manufacturerRespository.save(manufacturer);
        return SUCCESS_UPDATE_MANU;
    }

    @Transactional
    public String changeState(int id){
        Manufacturer manufacturer = findById(id);
        boolean isActive = manufacturer.isActive();
        manufacturer.setActive(!isActive);
        this.manufacturerRespository.save(manufacturer);
        return isActive ? SUCCESS_DISABLE_MANU : SUCCESS_ENABLE_MANU;
    }
}

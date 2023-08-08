package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Manufacturer;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_MANUFACTURER)
public class ManufacturerController {
    @Autowired
    ManufacturerService manufacturerService;

    @GetMapping(GET)
    public Set<ManufacturerDTO> getAllManufacturerActive() {
        return manufacturerService.findAllActiveDTO();
    }

    @GetMapping(GET_BY_ID)
    public ManufacturerDTO getManufacturerById(@PathVariable int id) {
        return manufacturerService.findByIdDTO(id);
    }

    @GetMapping(GET_ALL)
    public Set<ManufacturerDTO> getAllManufacturer() {
        return manufacturerService.findAll();
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = ADD)
    public ResponseEntity<String> add(@Valid @RequestBody ManufacturerDTO manufacturerDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(manufacturerService.addManufacturer(manufacturerDTO));
    }

    @PutMapping(value = EDIT_BY_ID)
    public ResponseEntity<String> edit(@PathVariable int id,@Valid @RequestBody ManufacturerDTO manufacturerDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        Manufacturer targetManufactor = manufacturerService.findById(id);
        targetManufactor.setName(manufacturerDTO.getName());
        return ResponseEntity.ok(manufacturerService.updateManufacture(targetManufactor));
    }

    @PutMapping(value = UPDATE_STATE_BY_ID)
    public ResponseEntity<String> updateState(@PathVariable int id){
        return ResponseEntity.ok(manufacturerService.changeState(id));
    }
}

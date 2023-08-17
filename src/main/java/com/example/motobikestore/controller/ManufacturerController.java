package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.CategoryDTO;
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

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String,Object>> add(@Valid @RequestBody ManufacturerDTO manufacturerDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        String message = manufacturerService.addManufacturer(manufacturerDTO);
        return map(message);
    }

    @PutMapping(value = EDIT)
    public ResponseEntity<Map<String,Object>> edit(@Valid @RequestBody ManufacturerDTO manufacturerDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        String message = manufacturerService.updateManufacture(manufacturerDTO);
        return map(message);
    }
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Map<String,Object>> deleteCategory(@PathVariable Integer id){
        String message = manufacturerService.deleteManufacturere(id);
        return map(message);
    }
    @PutMapping(value = UPDATE_STATE_BY_ID)
    public ResponseEntity<String> updateState(@PathVariable int id){
        return ResponseEntity.ok(manufacturerService.changeState(id));
    }
    private ResponseEntity<Map<String,Object>> map(String message){
        Map<String,Object> map = new HashMap<>();
        map.put("message",message);
        Set<ManufacturerDTO> manufacturerDTOS = getAllManufacturer();
        map.put("manufacturer",manufacturerDTOS);
        return ResponseEntity.ok(map);
    }
}

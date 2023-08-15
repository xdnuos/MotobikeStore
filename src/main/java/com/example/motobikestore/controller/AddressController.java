package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.AddressDTO;
import com.example.motobikestore.DTO.CartProductResponse;
import com.example.motobikestore.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ADDRESS)
public class AddressController {
    @Autowired
    AddressService addressService;

    @GetMapping(GET_BY_ID)
    public ResponseEntity<AddressDTO> getAddressByID(@PathVariable Long id){
        return ResponseEntity.ok(addressService.getByID(id));
    }

    @GetMapping(GET+"/user/{userID}")
    public ResponseEntity<List<AddressDTO>> getAddressByUserID(@PathVariable UUID userID){
        return ResponseEntity.ok(addressService.getAllByUserID(userID));
    }
    @PostMapping(ADD)
    public ResponseEntity<Map<String,Object>> addAddress(@RequestBody AddressDTO addressDTO){
        String message = addressService.addAddress(addressDTO);
        return getMapResponseEntity(message,addressDTO.getUserID());
    }
    @PutMapping(EDIT)
    public ResponseEntity<Map<String,Object>> editAddress(@RequestBody AddressDTO addressDTO){
        String message = addressService.editByID(addressDTO);
        return getMapResponseEntity(message,addressDTO.getUserID());
    }
    @PostMapping(DELETE)
    public ResponseEntity<Map<String,Object>> deleteAddress(@RequestBody AddressDTO addressDTO){
        String message = addressService.deleteByID(addressDTO.getAddressID());
        return getMapResponseEntity(message,addressDTO.getUserID());
    }
    private ResponseEntity<Map<String, Object>> getMapResponseEntity(String message, UUID userID) {
        List<AddressDTO> addressDTOS = addressService.getAllByUserID(userID);
        Map<String, Object> data = new HashMap<>();
        data.put("message",message);
        data.put("address",addressDTOS);
        return ResponseEntity.ok(data);
    }
}

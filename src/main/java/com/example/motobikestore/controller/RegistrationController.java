package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.user.CustomerRequest;
import com.example.motobikestore.DTO.user.UsersRequest;
import com.example.motobikestore.exception.InputFieldException;
import com.example.motobikestore.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.motobikestore.constants.PathConstants.ACTIVATE_CODE;
import static com.example.motobikestore.constants.PathConstants.API_V1_REGISTRATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_REGISTRATION)
public class RegistrationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> registration(@Valid @RequestBody CustomerRequest customerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputFieldException(bindingResult);
        }
        return ResponseEntity.ok(authenticationService.registerCustomer(customerRequest,customerRequest.getPassword2()));
    }

    @GetMapping(ACTIVATE_CODE)
    public ResponseEntity<String> activateEmailCode(@RequestParam String email,@RequestParam String code) {
        return ResponseEntity.ok(authenticationService.activateUser(email,code));
    }
}
package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.user.CustomerInfo;
import com.example.motobikestore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_CUSTOMER)
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping(GET+"/{phone}")
    public ResponseEntity<CustomerInfo> getCustomerInfo(@PathVariable String phone){
        System.out.println(phone);
        return  ResponseEntity.ok(customerService.getCustomerInfo(phone));
    }
}

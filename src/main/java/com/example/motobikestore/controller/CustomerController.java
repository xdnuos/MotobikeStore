package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.user.CustomerInfo;
import com.example.motobikestore.DTO.user.CustomerRequest;
import com.example.motobikestore.DTO.user.CustomerResponse;
import com.example.motobikestore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_CUSTOMER)
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping(GET+"/phone/{phone}")
    public ResponseEntity<CustomerInfo> getCustomerInfoByPhone(@PathVariable String phone){
        System.out.println(phone);
        return  ResponseEntity.ok(customerService.getCustomerInfo(phone));
    }
    @GetMapping(GET_ADMIN)
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return  ResponseEntity.ok(customerService.getCustomerWithStatistic());
    }
    @PutMapping(EDIT)
    public ResponseEntity<String> editCustomerInfo(@RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.editInfo(customerRequest));
    }
    @GetMapping(GET_BY_ID)
    public ResponseEntity<CustomerResponse> getCustomerInfo(@PathVariable(name = "id") UUID userID){
        return  ResponseEntity.ok(customerService.getInfo(userID));
    }
}

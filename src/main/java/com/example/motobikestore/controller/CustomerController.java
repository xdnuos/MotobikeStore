package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.StaffChangeState;
import com.example.motobikestore.DTO.user.CustomerBasicInfo;
import com.example.motobikestore.DTO.user.CustomerRequest;
import com.example.motobikestore.DTO.user.CustomerResponse;
import com.example.motobikestore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public ResponseEntity<CustomerBasicInfo> getBasicCustomerInfoByPhone(@PathVariable String phone){
        System.out.println(phone);
        return  ResponseEntity.ok(customerService.getCustomerInfo(phone));
    }
    @GetMapping(GET_ADMIN)
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return  ResponseEntity.ok(customerService.getListCustomerWithStatistic());
    }
    @GetMapping(GET_ADMIN+"/userID/{id}")
    public ResponseEntity<CustomerResponse> getCustomerByUserID(@PathVariable UUID id){
        return  ResponseEntity.ok(customerService.getCustomerWithStatisticByUserID(id));
    }
    @GetMapping(GET_ADMIN+"/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID id){
        return  ResponseEntity.ok(customerService.getCustomerWithStatistic(id));
    }
    @PutMapping(EDIT)
    public ResponseEntity<String> editCustomerInfo(@RequestBody CustomerRequest customerRequest){
        return ResponseEntity.ok(customerService.editInfo(customerRequest));
    }
    @GetMapping(GET_BY_ID)
    public ResponseEntity<CustomerResponse> getCustomerInfo(@PathVariable(name = "id") UUID userID){
        return  ResponseEntity.ok(customerService.getInfo(userID));
    }

    @PutMapping("/admin/changeState/{userID}") ResponseEntity<String> changeState(@PathVariable UUID userID){
        return ResponseEntity.ok(customerService.changeState(userID));
    }
    //    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MASTER')")
    @PutMapping("/admin/resetPassword/{userID}") ResponseEntity<String> resetPassword(@PathVariable UUID userID){
        return ResponseEntity.ok(customerService.resetPassword(userID));
    }
}

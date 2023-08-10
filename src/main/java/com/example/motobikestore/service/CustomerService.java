package com.example.motobikestore.service;

import com.example.motobikestore.DTO.user.CustomerInfo;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerInfo getCustomerInfo(String phone){
        CustomerInfo customerInfo = customerRepository.getInfoByPhone(phone)
                .orElseThrow(() -> new ApiRequestException("Customer not found", HttpStatus.NOT_FOUND));
        return customerInfo;
    }
}

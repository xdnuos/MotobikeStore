package com.example.motobikestore.service;

import com.example.motobikestore.DTO.AddressDTO;
import com.example.motobikestore.entity.Address;
import com.example.motobikestore.entity.Customer;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.AddressMapper;
import com.example.motobikestore.repository.AddressRepository;
import com.example.motobikestore.repository.CustomerRepository;
import com.example.motobikestore.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.motobikestore.constants.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UsersRepository usersRepository;
    private final AddressMapper addressMapper;
    private final CustomerRepository customerRepository;
    public String addAddress(AddressDTO addressDTO){
        Customer customer = customerRepository.findByUsers_UserID(addressDTO.getUserID())
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Address address = addressMapper.toEntity(addressDTO);
        address.setCustomer(customer);
        addressRepository.save(address);
        return "Success add new Address";
    }
    public List<AddressDTO> getAllByUserID(UUID userID){
        return addressRepository.findAllByCustomer_Users_UserID(userID).stream().map(addressMapper::toDto).collect(Collectors.toList());
    }
    public AddressDTO getByID(Long addressID){
        return addressRepository.findById(addressID).map(addressMapper::toDto)
                 .orElseThrow(() -> new ApiRequestException(ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
    public String editByID(AddressDTO addressDTO){
        Address address = addressRepository.findById(addressDTO.getAddressID())
                .orElseThrow(() -> new ApiRequestException(ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND));
        address.setAddress(addressDTO.getAddress());
        address.setFullname(addressDTO.getFullname());
        address.setPhone(addressDTO.getPhone());
        addressRepository.save(address);
        return "Succesfully edit address";
    }

    public String deleteByID(Long addressID){
        Address address = addressRepository.findById(addressID)
                .orElseThrow(() -> new ApiRequestException(ADDRESS_NOT_FOUND, HttpStatus.NOT_FOUND));
        addressRepository.delete(address);
        return "Succesfully delete address";
    }
}

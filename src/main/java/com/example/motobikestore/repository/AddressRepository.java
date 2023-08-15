package com.example.motobikestore.repository;

import com.example.motobikestore.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByCustomer_Users_UserID(UUID userID);
}
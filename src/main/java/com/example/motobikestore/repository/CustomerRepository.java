package com.example.motobikestore.repository;

import com.example.motobikestore.DTO.user.CustomerInfo;
import com.example.motobikestore.entity.Customer;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByPhone(String phone);
    Optional<Customer> findByUsers_UserID(UUID uuid);
    @Query("SELECT new com.example.motobikestore.DTO.user.CustomerInfo(c.customerID, c.phone,c.users.firstName,c.users.lastName) FROM Customer c WHERE c.phone = :phone")
    Optional<CustomerInfo> getInfoByPhone(@PathParam("phone") String phone);
}
package com.example.motobikestore.repository;

import com.example.motobikestore.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    Optional<Staff> findByUsers_UserID(UUID uuid);
}

package com.example.motobikestore.repository.jpa;

import com.example.motobikestore.DTO.SizeDTO;
import com.example.motobikestore.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("select new com.example.motobikestore.DTO.SizeDTO (s.name,s.stock ) from Size as s")
    List<SizeDTO> findAllSize();
}

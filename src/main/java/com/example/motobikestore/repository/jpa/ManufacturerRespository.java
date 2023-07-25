package com.example.motobikestore.repository.jpa;

import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManufacturerRespository extends JpaRepository<Manufacturer,Integer> {
    @Query("select new com.example.motobikestore.DTO.ManufacturerDTO (c.manufacturerID,c.name ) from Manufacturer as c where c.isActive =true")
    List<ManufacturerDTO> findAllActive ();

    @Query("from Category where isActive =true and categoryID = :id")
    Optional<ManufacturerDTO> findAllActiveById (@Param("id") int id);

    @Query("select new com.example.motobikestore.DTO.ManufacturerDTO (c.manufacturerID,c.name ) from Manufacturer as c")
    List<ManufacturerDTO> findAllNew();

    @Query("select new com.example.motobikestore.DTO.ManufacturerDTO (c.manufacturerID,c.name ) from Manufacturer as c where c.manufacturerID = :id")
    Optional<ManufacturerDTO> findByIdDTO(@Param("id") int id);
}

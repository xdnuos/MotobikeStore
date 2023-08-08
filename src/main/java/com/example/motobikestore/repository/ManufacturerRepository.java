package com.example.motobikestore.repository;

import com.example.motobikestore.DTO.ManufacturerDTO;
import com.example.motobikestore.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Integer> {
    @Query("select new com.example.motobikestore.DTO.ManufacturerDTO (c.manufacturerID,c.name ) from Manufacturer as c where c.isActive =true")
    Set<ManufacturerDTO> findAllActive ();

    @Query("from Category where isActive =true and categoryID = :id")
    Optional<ManufacturerDTO> findAllActiveById (@Param("id") int id);

    @Query("select new com.example.motobikestore.DTO.ManufacturerDTO (c.manufacturerID,c.name ) from Manufacturer as c")
    Set<ManufacturerDTO> findAllNew();

    @Query("select new com.example.motobikestore.DTO.ManufacturerDTO (c.manufacturerID,c.name ) from Manufacturer as c where c.manufacturerID = :id")
    Optional<ManufacturerDTO> findByIdDTO(@Param("id") int id);

    Boolean existsByName(String name);
}

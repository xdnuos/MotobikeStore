package com.example.motobikestore.repository.jpa;

import com.example.motobikestore.DTO.CategoryDTO;
import com.example.motobikestore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query("select new com.example.motobikestore.DTO.CategoryDTO (c.categoryID,c.name ) from Category as c where c.isActive =true")
    List<CategoryDTO> findAllActive ();

    @Query("from Category where isActive =true and categoryID = :id")
    Optional<CategoryDTO> findAllActiveById (@Param("id") int id);

    @Query("select new com.example.motobikestore.DTO.CategoryDTO (c.categoryID,c.name ) from Category as c")
    List<CategoryDTO> findAllNew();

    @Query("select new com.example.motobikestore.DTO.CategoryDTO (c.categoryID,c.name ) from Category as c where c.categoryID = :id")
    Optional<CategoryDTO> findByIdDTO(@Param("id") int id);
}

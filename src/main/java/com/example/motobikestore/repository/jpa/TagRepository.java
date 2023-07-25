package com.example.motobikestore.repository.jpa;

import com.example.motobikestore.DTO.TagDTO;
import com.example.motobikestore.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Integer> {
    @Query("select new com.example.motobikestore.DTO.TagDTO (c.tagID,c.name ) from Tag as c where c.isActive =true")
    List<TagDTO> findAllActive ();

    @Query("from Tag where isActive =true and tagID = :id")
    Optional<TagDTO> findAllActiveById (@Param("id") int id);

    @Query("select new com.example.motobikestore.DTO.TagDTO (c.tagID,c.name ) from Tag as c")
    List<TagDTO> findAllNew();

    @Query("select new com.example.motobikestore.DTO.TagDTO (c.tagID,c.name ) from Tag as c where c.tagID = :id")
    Optional<TagDTO> findByIdDTO(@Param("id") int id);
}

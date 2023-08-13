package com.example.motobikestore.repository;

import com.example.motobikestore.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StockRepository extends JpaRepository<Stock,Long>{
}
package com.example.motobikestore.repository.blaze;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.example.motobikestore.view.StockView;

public interface StockViewRepository extends EntityViewRepository<StockView, Long> {
}

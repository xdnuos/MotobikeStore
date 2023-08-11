package com.example.motobikestore.repository.blaze;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.example.motobikestore.view.OrdersAdminView;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Repository
public interface OrderAdminViewRepository extends EntityViewRepository<OrdersAdminView, Long> {
    List<OrdersAdminView> findAllByStaffStaffID(UUID staffID);
}

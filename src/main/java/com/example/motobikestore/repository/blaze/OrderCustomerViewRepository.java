package com.example.motobikestore.repository.blaze;

import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import com.example.motobikestore.entity.Orders;
import com.example.motobikestore.view.OrdersAdminView;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Repository
public interface OrderCustomerViewRepository extends EntityViewRepository<Orders, Long> {
    List<OrdersAdminView> findAllByCustomer_Users_UserID(UUID userID);
}

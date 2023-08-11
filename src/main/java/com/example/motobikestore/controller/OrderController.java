package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.ConfirmOrderDTO;
import com.example.motobikestore.DTO.OrderRequestAdmin;
import com.example.motobikestore.service.OrderService;
import com.example.motobikestore.view.OrdersAdminView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ORDER)
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping(GET_ADMIN+"/{staffID}")
    public List<OrdersAdminView> getOrderByAdmin(@PathVariable UUID staffID){
        return orderService.getOrderByAdmin(staffID);
    }
    @GetMapping(GET_ADMIN)
    public Iterable<OrdersAdminView> getOrderAdmin(){
        return orderService.getOrderAdmin();
    }
    @PostMapping(ADD_ADMIN)
    public ResponseEntity<String> addOrderAdmin(@RequestBody OrderRequestAdmin orderRequestAdmin){
        return ResponseEntity.ok(orderService.createOrderAdmin(orderRequestAdmin));
    }
    @PutMapping(EDIT_ADMIN+"/confirmOrder")
    public ResponseEntity<String> confirmOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO){
        return ResponseEntity.ok(orderService.confirmOrder(confirmOrderDTO));
    }
}

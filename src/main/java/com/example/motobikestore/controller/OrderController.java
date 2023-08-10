package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.OrderRequestAdmin;
import com.example.motobikestore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.motobikestore.constants.PathConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_V1_ORDER)
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping(ADD)
    public ResponseEntity<String> addOrderAdmin(@RequestBody OrderRequestAdmin orderRequestAdmin){
        return ResponseEntity.ok(orderService.createOrderAdmin(orderRequestAdmin));
    }
}

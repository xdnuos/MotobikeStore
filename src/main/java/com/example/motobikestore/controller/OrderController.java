package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.ConfirmOrderDTO;
import com.example.motobikestore.DTO.OrderRequestAdmin;
import com.example.motobikestore.DTO.OrderRequestCustomer;
import com.example.motobikestore.entity.OrderItem;
import com.example.motobikestore.service.OrderService;
import com.example.motobikestore.view.OrdersAdminView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    @GetMapping(GET_ADMIN+"/user/{userID}")
    public List<OrdersAdminView> getOrderByUserID(@PathVariable UUID userID){
        return orderService.getOrderByUserID(userID);
    }
    @GetMapping(GET_ADMIN)
    public Iterable<OrdersAdminView> getOrderAdmin(){
        return orderService.getOrderAdmin();
    }
    @GetMapping(GET+"/orderDetail/{id}")
    public OrdersAdminView getOrderAdmin(@PathVariable Long id){
        return orderService.getOrderDetail(id);
    }
    @GetMapping(GET_BY_ID)
    public Iterable<OrdersAdminView> getOrderByCustomer(@PathVariable(name = "id") UUID customerID){
        return orderService.getOrderCustomer(customerID);
    }
    @PostMapping(ADD_ADMIN)
    public ResponseEntity<Map<String,Object>> addOrderForAdmin(@RequestBody OrderRequestAdmin orderRequestAdmin){
        return ResponseEntity.ok(orderService.createOrderAdmin(orderRequestAdmin));
    }
    @PostMapping(ADD)
    public ResponseEntity<String> addOrderForUser(@RequestBody OrderRequestCustomer orderRequestCustomer){
        return ResponseEntity.ok(orderService.createOrderForCustomer(orderRequestCustomer));
    }
    @PutMapping(EDIT_ADMIN+"/confirm")
    public ResponseEntity<String> confirmOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO){
        return ResponseEntity.ok(orderService.confirmOrder(confirmOrderDTO));
    }
    @PutMapping(EDIT_ADMIN+"/shipping")
    public ResponseEntity<String> shippingOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO){
        return ResponseEntity.ok(orderService.shippingOrder(confirmOrderDTO));
    }
    @PutMapping(EDIT_ADMIN+"/success")
    public ResponseEntity<String> successOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO){
        return ResponseEntity.ok(orderService.successOrder(confirmOrderDTO));
    }
    @PutMapping(EDIT_ADMIN+"/cancel")
    public ResponseEntity<String> cancelOrder(@RequestBody ConfirmOrderDTO confirmOrderDTO){
        return ResponseEntity.ok(orderService.cancelOrder(confirmOrderDTO));
    }

    @DeleteMapping(DELETE+"/orderItem/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id){
        return ResponseEntity.ok(orderService.removeProductInOrder(id));
    }
}

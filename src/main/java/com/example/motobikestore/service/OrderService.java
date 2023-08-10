package com.example.motobikestore.service;

import com.example.motobikestore.DTO.OrderRequestAdmin;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.enums.OrderStatus;
import com.example.motobikestore.enums.Payment;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final UsersRepository usersRepository;
    private final StaffRepository staffRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    @Transactional
    public String createOrderAdmin(OrderRequestAdmin orderRequestAdmin){
        Customer customer = new Customer();
        Staff staff = staffRepository.findByUsers_UserID(orderRequestAdmin.getUserID())
                .orElseThrow(() -> new ApiRequestException("Staff not found", HttpStatus.NOT_FOUND));

        if(orderRequestAdmin.getCustomerID()!=null){
            customer = customerRepository.findById(orderRequestAdmin.getCustomerID())
                    .orElseThrow(() -> new ApiRequestException("Customer not found", HttpStatus.NOT_FOUND));
        }else {
            boolean isCustomerExits = customerRepository.findByPhone(orderRequestAdmin.getPhone()).isPresent();
            if (isCustomerExits){
                throw new ApiRequestException("Customer is exits", HttpStatus.NOT_FOUND);
            }
            Users users = new Users();
            users.setFirstName(orderRequestAdmin.getFirstName());
            users.setLastName(orderRequestAdmin.getLastName());
            users.setCreateDate(LocalDateTime.now());
            users.setRoles(Collections.singleton(Role.CUSTOMER));
            customer.setPhone(orderRequestAdmin.getPhone());
            customer.setUsers(users);
            customerRepository.save(customer);
        }
        List<CartProduct> cartProducts = cartProductRepository.findAllById(orderRequestAdmin.getCartProductIDs());
        List<OrderItem> orderItems = new ArrayList<>();
        Orders orders = new Orders();
        cartProducts.forEach(cartProduct -> {
            OrderItem orderItem =  new OrderItem();
            orderItem.setPrice(cartProduct.getProduct().getPrice());
            orderItem.setProduct(cartProduct.getProduct());
            orderItem.setQuantity(cartProduct.getQuantity());
            orderItems.add(orderItem);
            orderItem.setOrders(orders);
            reduceProductNumber(cartProduct.getProduct(),cartProduct.getQuantity());
        });
        orders.setOrderItems(orderItems);
        orders.setStaff(staff);
        orders.setOrderTime(LocalDateTime.now());
        orders.setTotal(orders.getTotalOrderPrice());
        orders.setPhone(orderRequestAdmin.getPhone());
        orders.setFullname(orderRequestAdmin.getFirstName()+" "+ orderRequestAdmin.getLastName());
        orders.setCustomer(customer);
        orders.setOrderStatus(OrderStatus.SUCCESS);
        orders.setPayment(Payment.LIVE);
        cartProductRepository.deleteAll(cartProducts);
        ordersRepository.save(orders);
        return "Order success";
    }

    @Transactional
    private void reduceProductNumber(Product product,Integer quantity){
        Integer stock = product.getStock();
        if (quantity > stock){
            throw new ApiRequestException("Invalid quantity", HttpStatus.NOT_FOUND);
        }
        product.setStock(stock-quantity);
        productRepository.save(product);
    }
}

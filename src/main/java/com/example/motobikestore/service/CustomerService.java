package com.example.motobikestore.service;

import com.example.motobikestore.DTO.user.CustomerInfo;
import com.example.motobikestore.DTO.user.CustomerResponse;
import com.example.motobikestore.entity.Customer;
import com.example.motobikestore.entity.Orders;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.OrderStatus;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.CustomerResponseMapper;
import com.example.motobikestore.mapper.UsersResponseMapper;
import com.example.motobikestore.repository.CustomerRepository;
import com.example.motobikestore.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UsersResponseMapper usersResponseMapper;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerResponseMapper customerResponseMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
    }

    public CustomerInfo getCustomerInfo(String phone){
        CustomerInfo customerInfo = customerRepository.getInfoByPhone(phone)
                .orElseThrow(() -> new ApiRequestException("Customer not found", HttpStatus.NOT_FOUND));
        return customerInfo;
    }

    public List<CustomerResponse> getCustomerWithStatistic(){
        Role role = Role.CUSTOMER;
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        List<Customer> customers = customerRepository.findAllByUsers_Roles(roles);
        List<CustomerResponse>  customerResponses = new ArrayList<>();
        customers.forEach(customer -> {
            AtomicReference<BigDecimal> totalPurchased = new AtomicReference<>(BigDecimal.ZERO);
            AtomicReference<Integer> successOrder = new AtomicReference<>(0);
            AtomicReference<Integer> totalProductBuy = new AtomicReference<>(0);
            List<Orders> orders = customer.getOrders();
            orders.forEach(order ->{
                if (order.getOrderStatus().equals(OrderStatus.SUCCESS)){
                    successOrder.getAndSet(successOrder.get() + 1);
                    totalPurchased.updateAndGet(current -> current.add(order.getTotal()));
                    order.getOrderItems().forEach(orderItem -> {
                        totalProductBuy.getAndSet(totalProductBuy.get() + orderItem.getQuantity());
                    });
                }
            });
            CustomerResponse customerResponse = customerResponseMapper.toDto(customer);
            customerResponse.setTotalOrders(orders.size());
            customerResponse.setTotalProductBuy(totalProductBuy.get());
            customerResponse.setTotalPurchased(totalPurchased.get());
            Float ratioOrder = (float)0;
            if (orders.size()!=0)
            {
                ratioOrder = ((float) (successOrder.get()) / orders.size());
            }
            customerResponse.setRatioOrder(ratioOrder);

            Users users = customer.getUsers();
            customerResponse.setEmail(users.getEmail());
            customerResponse.setFirstName(users.getFirstName());
            customerResponse.setLastName(users.getLastName());
            customerResponse.setIsActive(users.isActive());
            customerResponses.add(customerResponse);
        });
        return customerResponses;
    }
}

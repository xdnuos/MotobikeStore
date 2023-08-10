package com.example.motobikestore.service;

import com.example.motobikestore.DTO.OrderRequestAdmin;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.repository.CartProductRepository;
import com.example.motobikestore.repository.CustomerRepository;
import com.example.motobikestore.repository.OrdersRepository;
import com.example.motobikestore.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CartProductRepository cartProductRepository;
    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrderWithCustomer() {
        UUID uuid = UUID.randomUUID();
        OrderRequestAdmin orderRequestAdmin = new OrderRequestAdmin();
        orderRequestAdmin.setUserID(uuid);
        orderRequestAdmin.setCustomerID(uuid);

        Customer customer = new Customer();
        when(customerRepository.findById(uuid)).thenReturn(Optional.of(customer));

        Staff staff = new Staff();
        when(staffRepository.findByUsers_UserID(uuid)).thenReturn(Optional.of(staff));

        List<CartProduct> cartProducts = new ArrayList<>();
        when(cartProductRepository.findAllById(any())).thenReturn(cartProducts);

        String result = orderService.createOrderAdmin(orderRequestAdmin);
        verify(customerRepository, times(1)).findById(uuid);
        verify(staffRepository, times(1)).findByUsers_UserID(uuid);
        verify(cartProductRepository, times(1)).findAllById(any());
         assertEquals("Order success", result);
    }
    @Test
    void testCreateOrderWithoutCustomer() {
        UUID uuid = UUID.randomUUID();
        OrderRequestAdmin orderRequestAdmin = new OrderRequestAdmin();
        orderRequestAdmin.setUserID(uuid);
        orderRequestAdmin.setFirstName("John");
        orderRequestAdmin.setLastName("Doe");
        orderRequestAdmin.setPhone("123-456-7890");

        Customer customer = new Customer();
        Users users = new Users();
        users.setFirstName(orderRequestAdmin.getFirstName());
        users.setLastName(orderRequestAdmin.getLastName());
        users.setCreateDate(LocalDateTime.now());
        users.setRoles(Collections.singleton(Role.CUSTOMER));
        customer.setPhone(orderRequestAdmin.getPhone());
        customer.setUsers(users);

        Staff staff = new Staff();
        when(staffRepository.findByUsers_UserID(uuid)).thenReturn(Optional.of(staff));

        List<CartProduct> cartProducts = new ArrayList<>();
        when(cartProductRepository.findAllById(any())).thenReturn(cartProducts);

        String result = orderService.createOrderAdmin(orderRequestAdmin);
        Orders orders = new Orders();
//        orders.setStaff(staff);
        orders.setCustomer(customer);
        verify(staffRepository, times(1)).findByUsers_UserID(uuid);
        verify(cartProductRepository, times(1)).findAllById(any());
        verify(ordersRepository).save(eq(orders));
        assertEquals("Order success", result);
    }

}


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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    public void testCreateOrderAdmin_Success() {
        // Mocking data
        OrderRequestAdmin orderRequestAdmin = new OrderRequestAdmin(); // Initialize with necessary data

        Staff staff = new Staff(); // Initialize with necessary data
        when(staffRepository.findByUsers_UserID(any())).thenReturn(Optional.of(staff));

        Customer customer = new Customer(); // Initialize with necessary data
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        // Mocking cartProducts
        List<CartProduct> cartProducts = new ArrayList<>(); // Initialize with necessary data
        when(cartProductRepository.findAllById(any())).thenReturn(cartProducts);

        Orders savedOrder = new Orders(); // Initialize with necessary data
        when(ordersRepository.save(any())).thenReturn(savedOrder);

        // Execute the method
        Map<String, Object> result = orderService.createOrderAdmin(orderRequestAdmin);

        // Verify the interactions
        verify(staffRepository, times(1)).findByUsers_UserID(any());
        verify(customerRepository, times(0)).findById(any()); // Assuming customerID is not null in orderRequestAdmin
        verify(cartProductRepository, times(1)).findAllById(any());
        verify(ordersRepository, times(1)).save(any());

        // Assertions
        assertNotNull(result);
        System.out.println(result);
        assertEquals("Order success", result.get("message"));
        assertEquals(savedOrder.getOrderID(), result.get("orderID"));
    }
//    @Test
//    void testCreateOrderWithoutCustomer() {
//        UUID uuid = UUID.randomUUID();
//        OrderRequestAdmin orderRequestAdmin = new OrderRequestAdmin();
//        orderRequestAdmin.setUserID(uuid);
//        orderRequestAdmin.setFirstName("John");
//        orderRequestAdmin.setLastName("Doe");
//        orderRequestAdmin.setPhone("123-456-7890");
//
//        Customer customer = new Customer();
//        Users users = new Users();
//        users.setFirstName(orderRequestAdmin.getFirstName());
//        users.setLastName(orderRequestAdmin.getLastName());
//        users.setCreateDate(LocalDateTime.now());
//        users.setRoles(Collections.singleton(Role.CUSTOMER));
//        customer.setPhone(orderRequestAdmin.getPhone());
//        customer.setUsers(users);
//
//        Staff staff = new Staff();
//        when(staffRepository.findByUsers_UserID(uuid)).thenReturn(Optional.of(staff));
//
//        List<CartProduct> cartProducts = new ArrayList<>();
//        when(cartProductRepository.findAllById(any())).thenReturn(cartProducts);
//
//        String result = orderService.createOrderAdmin(orderRequestAdmin);
//        Orders orders = new Orders();
////        orders.setStaff(staff);
//        orders.setCustomer(customer);
//        verify(staffRepository, times(1)).findByUsers_UserID(uuid);
//        verify(cartProductRepository, times(1)).findAllById(any());
//        verify(ordersRepository).save(eq(orders));
//        assertEquals("Order success", result);
//    }

}


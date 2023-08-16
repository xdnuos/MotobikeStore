package com.example.motobikestore.service;

import com.example.motobikestore.DTO.ConfirmOrderDTO;
import com.example.motobikestore.DTO.OrderRequestAdmin;
import com.example.motobikestore.DTO.OrderRequestCustomer;
import com.example.motobikestore.entity.*;
import com.example.motobikestore.enums.OrderStatus;
import com.example.motobikestore.enums.Payment;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.repository.*;
import com.example.motobikestore.repository.blaze.OrderAdminViewRepository;
import com.example.motobikestore.repository.blaze.OrderCustomerViewRepository;
import com.example.motobikestore.view.OrdersAdminView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartProductRepository cartProductRepository;
    private final CustomerRepository customerRepository;
    private final UsersRepository usersRepository;
    private final StaffRepository staffRepository;
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final OrderAdminViewRepository orderAdminViewRepository;
    private final AddressRepository addressRepository;
    private final OrderCustomerViewRepository orderCustomerViewRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Map<String,Object> createOrderAdmin(OrderRequestAdmin orderRequestAdmin){
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
        Orders orders = new Orders();

        List<CartProduct> cartProducts = cartProductRepository.findAllById(orderRequestAdmin.getCartProductIDs());
        List<OrderItem> orderItems = new ArrayList<>();
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
        Map<String,Object> map = new HashMap<>();
        map.put("message","Order success");
        map.put("orderID",orders.getOrderID());
        return map;
    }
    @Transactional
    public String createOrderForCustomer(OrderRequestCustomer orderRequestCustomer){
        System.out.println("dddddddddddddddddddd");
        System.out.println(orderRequestCustomer.getAddressID());
        Customer customer = customerRepository.findByUsers_UserID(orderRequestCustomer.getUserID())
                    .orElseThrow(() -> new ApiRequestException("Customer not found", HttpStatus.NOT_FOUND));
        Address address = addressRepository.findById(orderRequestCustomer.getAddressID())
                .orElseThrow(() -> new ApiRequestException("Address not found", HttpStatus.NOT_FOUND));
        Orders orders = new Orders();
        List<CartProduct> cartProducts = cartProductRepository.findAllById(orderRequestCustomer.getCartProductIDs());
        List<OrderItem> orderItems = new ArrayList<>();
        cartProducts.forEach(cartProduct -> {
            OrderItem orderItem =  new OrderItem();
            orderItem.setPrice(cartProduct.getProduct().getPrice());
            orderItem.setProduct(cartProduct.getProduct());
            orderItem.setQuantity(cartProduct.getQuantity());
            orderItems.add(orderItem);
            orderItem.setOrders(orders);
        });
        orders.setOrderItems(orderItems);
        orders.setOrderTime(LocalDateTime.now());
        orders.setTotal(orders.getTotalOrderPrice());
        orders.setPhone(address.getPhone());
        orders.setFullname(address.getFullname());
        orders.setNote(orderRequestCustomer.getNote());
        orders.setAddress(address.getAddress());
        orders.setCustomer(customer);
        orders.setOrderStatus(OrderStatus.PENDING);
        orders.setPayment(orderRequestCustomer.getPayment());
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
        product.setSaleCount(quantity);
        productRepository.save(product);
    }

    public List<OrdersAdminView> getOrderByAdmin(UUID staffID){
        return orderAdminViewRepository.findAllByStaffStaffID(staffID);
    }
    public Iterable<OrdersAdminView> getOrderAdmin(){
        return orderAdminViewRepository.findAll();
    }
    public OrdersAdminView getOrderDetail(Long orderID){
        return orderAdminViewRepository.findByOrderID(orderID);
    }
    public Iterable<OrdersAdminView> getOrderCustomer(UUID userID){
        return orderCustomerViewRepository.findAllByCustomer_Users_UserID(userID);
    }

    @Transactional
    public String confirmOrder(ConfirmOrderDTO confirmOrderDTO){
        Orders orders = ordersRepository.findById(confirmOrderDTO.getOrderID())
                .orElseThrow(() -> new ApiRequestException("Order not found", HttpStatus.NOT_FOUND));
        if(orders.getStaff()!=null & !orders.getOrderStatus().equals(OrderStatus.FAILED)){
            throw new ApiRequestException("Order already confirm", HttpStatus.NOT_FOUND);
        }
        Staff staff = staffRepository.findByUsers_UserID(confirmOrderDTO.getUserID())
                .orElseThrow(()-> new ApiRequestException("Staff not found", HttpStatus.NOT_FOUND));
//        giam so luong mat hang trong kho
        orders.getOrderItems().forEach(orderItem -> {
            reduceProductNumber(orderItem.getProduct(),orderItem.getQuantity());
        });
        orders.setStaff(staff);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        ordersRepository.save(orders);
        return "Order confirm successfully";
    }
    @Transactional
    public String shippingOrder(ConfirmOrderDTO confirmOrderDTO){
        Orders orders = ordersRepository.findById(confirmOrderDTO.getOrderID())
                .orElseThrow(() -> new ApiRequestException("Order not found", HttpStatus.NOT_FOUND));
        checkConfirm(orders);
        Staff staff = staffRepository.findByUsers_UserID(confirmOrderDTO.getUserID())
                .orElseThrow(()-> new ApiRequestException("Staff not found", HttpStatus.NOT_FOUND));
        if(orders.getOrderStatus().equals(OrderStatus.DELIVERING)){
            throw new ApiRequestException("Order already shipping", HttpStatus.NOT_FOUND);
        }
        orders.setStaff(staff);
        orders.setOrderStatus(OrderStatus.DELIVERING);
        ordersRepository.save(orders);
        return "Successfully change order to shipping";
    }
    private void checkConfirm(Orders orders){
        if(orders.getStaff()==null | orders.getOrderStatus().equals(OrderStatus.FAILED)){
            throw new ApiRequestException("You need confirm order first", HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    public String successOrder(ConfirmOrderDTO confirmOrderDTO){
        Orders orders = ordersRepository.findById(confirmOrderDTO.getOrderID())
                .orElseThrow(() -> new ApiRequestException("Order not found", HttpStatus.NOT_FOUND));
        checkConfirm(orders);
        if(orders.getOrderStatus().equals(OrderStatus.SUCCESS)){
            throw new ApiRequestException("Order already confirm success", HttpStatus.NOT_FOUND);
        }
        Staff staff = staffRepository.findByUsers_UserID(confirmOrderDTO.getUserID())
                .orElseThrow(()-> new ApiRequestException("Staff not found", HttpStatus.NOT_FOUND));
        orders.setStaff(staff);
        orders.setOrderStatus(OrderStatus.SUCCESS);
        ordersRepository.save(orders);
        return "Successfully change order to success";
    }
    @Transactional
    public String cancelOrder(ConfirmOrderDTO confirmOrderDTO){
        Orders orders = ordersRepository.findById(confirmOrderDTO.getOrderID())
                .orElseThrow(() -> new ApiRequestException("Order not found", HttpStatus.NOT_FOUND));

        if(orders.getOrderStatus().equals(OrderStatus.FAILED)){
            throw new ApiRequestException("Order already confirm cancel", HttpStatus.NOT_FOUND);
        }
        Staff staff = staffRepository.findByUsers_UserID(confirmOrderDTO.getUserID())
                .orElseThrow(()-> new ApiRequestException("Staff not found", HttpStatus.NOT_FOUND));
        if(orders.getStaff()!=null){
            orders.getOrderItems().forEach(orderItem -> {
                reduceProductNumber(orderItem.getProduct(),-orderItem.getQuantity());
            });
        }
        orders.setStaff(staff);
        orders.setOrderStatus(OrderStatus.FAILED);
        ordersRepository.save(orders);
        return "Successfully change order to cancel";
    }

    @Transactional
    public String removeProductInOrder(Long orderItemID){
        OrderItem orderItem = orderItemRepository.findById(orderItemID)
                .orElseThrow(() -> new ApiRequestException("Product not found", HttpStatus.NOT_FOUND));
        orderItemRepository.delete(orderItem);
        return "Success remove item from order";
    }
}

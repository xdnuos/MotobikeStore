package com.example.motobikestore.service;

import com.example.motobikestore.DTO.StaffChangeState;
import com.example.motobikestore.DTO.user.CustomerBasicInfo;
import com.example.motobikestore.DTO.user.CustomerRequest;
import com.example.motobikestore.DTO.user.CustomerResponse;
import com.example.motobikestore.constants.ErrorMessage;
import com.example.motobikestore.constants.SuccessMessage;
import com.example.motobikestore.entity.Customer;
import com.example.motobikestore.entity.Images;
import com.example.motobikestore.entity.Orders;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.enums.OrderStatus;
import com.example.motobikestore.enums.Role;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.CustomerResponseMapper;
import com.example.motobikestore.repository.CustomerRepository;
import com.example.motobikestore.repository.ImagesRepository;
import com.example.motobikestore.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.motobikestore.constants.ErrorMessage.USER_NOT_FOUND;
import static com.example.motobikestore.constants.SuccessMessage.SUCCESS_DISABLE_USER;
import static com.example.motobikestore.constants.SuccessMessage.SUCCESS_ENABLE_USER;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public CustomerService(CustomerRepository customerRepository,
                           CustomerResponseMapper customerResponseMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
    }

    public CustomerBasicInfo getCustomerInfo(String phone){
        CustomerBasicInfo customerBasicInfo = customerRepository.getInfoByPhone(phone)
                .orElseThrow(() -> new ApiRequestException("Customer not found", HttpStatus.NOT_FOUND));
        return customerBasicInfo;
    }

    public List<CustomerResponse> getListCustomerWithStatistic(){
        Role role = Role.CUSTOMER;
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        List<Customer> customers = customerRepository.findAllByUsers_Roles(roles);
        List<CustomerResponse>  customerResponses = new ArrayList<>();
        customers.forEach(customer -> {
            CustomerResponse customerResponse= getStatistic(customer);
            customerResponses.add(customerResponse);
        });
        return customerResponses;
    }

    public CustomerResponse getCustomerWithStatisticByUserID(UUID userID){
        Customer customer = customerRepository.findByUsers_UserID(userID)
                            .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return getStatistic(customer);
    }
    public CustomerResponse getCustomerWithStatistic(UUID customerID){
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return getStatistic(customer);
    }

    private CustomerResponse getStatistic(Customer customer){
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
        customerResponse.setUserID(users.getUserID());
        return customerResponse;
    }
    public String editInfo(CustomerRequest customerRequest){
        Customer customer = customerRepository.findByUsers_UserID(customerRequest.getUserID())
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        customer.setBirth(customerRequest.getBirth());
        customer.setPhone(customerRequest.getPhone());
        customer.setSex(customerRequest.getSex());
//        customer.getUsers().setEmail(customerRequest.getEmail());
        customer.getUsers().setFirstName(customerRequest.getPhone());
        customer.getUsers().setLastName(customerRequest.getLastName());

        if(customer.getUsers().getAvatar()!=null){
            Long ImageID = customer.getUsers().getAvatar().getImageID();
            Images images = imagesRepository.findById(ImageID).get();
            if (images!=null){
                cloudinaryService.deleteImage(images.getImagePath());
                imagesRepository.delete(images);
                            }
        }
        if (customerRequest.getImg()!=null){
            MultipartFile avatar = customerRequest.getImg();
            String img = cloudinaryService.uploadImage(avatar);
            Images images = new Images();
            images.setImagePath(img);
            imagesRepository.save(images);
        }
        customerRepository.save(customer);
        return SuccessMessage.SUCCESS_UPDATE_USER;
    }

    public CustomerResponse getInfo(UUID userID){
        Customer customer = customerRepository.findByUsers_UserID(userID)
                .orElseThrow(() -> new ApiRequestException(ErrorMessage.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Users users = customer.getUsers();
        CustomerResponse customerResponse = customerResponseMapper.toDto(customer);
        customerResponse.setEmail(users.getEmail());
        customerResponse.setFirstName(users.getFirstName());
        customerResponse.setLastName(users.getLastName());
        customerResponse.setAvatarUrl(users.getAvatar().getImagePath());
        return customerResponse;
    }

    @Transactional
    public String changeState(UUID userID){
        Users customerUser = usersRepository.findById(userID)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        boolean isActive = customerUser.isActive();
        usersRepository.changeStatusByID(customerUser.getUserID(),!isActive);
        return isActive ? SUCCESS_DISABLE_USER : SUCCESS_ENABLE_USER;
    }

    @Transactional
    public String resetPassword(UUID uuid){
        Users users = usersRepository.findById(uuid)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        users.setPassword(passwordEncoder.encode("123456"));
        usersRepository.save(users);

        return "Success. Default password is 123456";
    }
}

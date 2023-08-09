package com.example.motobikestore.service;

import com.example.motobikestore.DTO.CartProductResponse;
import com.example.motobikestore.entity.CartProduct;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.exception.ApiRequestException;
import com.example.motobikestore.mapper.CartProductResponseMapper;
import com.example.motobikestore.repository.CartProductRepository;
import com.example.motobikestore.repository.ProductRepository;
import com.example.motobikestore.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.motobikestore.constants.ErrorMessage.*;

@Service
public class CartService {

    @Autowired
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final CartProductResponseMapper cartProductResponseMapper;

    public CartService(CartProductRepository cartProductRepository,
                       ProductRepository productRepository,
                       UsersRepository usersRepository,
                       CartProductResponseMapper cartProductResponseMapper) {
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
        this.cartProductResponseMapper = cartProductResponseMapper;
    }

    public List<CartProductResponse> getCartItem(UUID uuid){
        return cartProductRepository.findAllByUsers_UserID(uuid).stream().map(cartProductResponseMapper::toDto).collect(Collectors.toList());
    }
    @Transactional
    public String addItem2Cart(Long productID, String email,Integer quantity){
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        CartProduct cartProduct = cartProductRepository.findByCartProductIDAndUsers(productID,email);
        if (quantity>product.getStock()){
            return "Invalid quantity";
        }
        if(cartProduct!=null){
            quantity += cartProduct.getQuantity();
            if(quantity<=0){
                return removeItem(cartProduct);
            }
        }else {
            cartProduct = new CartProduct();
            Users users = usersRepository.findByEmail(email)
                    .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND, HttpStatus.NOT_FOUND));
            cartProduct.setProduct(product);
            cartProduct.setUsers(users);
        }

        cartProduct.setQuantity(quantity);
        cartProductRepository.save(cartProduct);
        return "Added to cart";
    }
    private String removeItem(CartProduct cartProduct){
        cartProduct.setProduct(null);
        cartProduct.setUsers(null);
        cartProductRepository.delete(cartProduct);
        return "Success remove item from cart";
    }
    @Transactional
    public String removeItem2Cart(Long id){
        CartProduct cartProduct = cartProductRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Product not found", HttpStatus.NOT_FOUND));
        return removeItem(cartProduct);
    }

    @Transactional
    public String updateQuantity(Long cartProductID,Integer quantity){
        CartProduct cartProduct = cartProductRepository.findById(cartProductID)
                .orElseThrow(() -> new ApiRequestException("Product not found", HttpStatus.NOT_FOUND));
        if(quantity>cartProduct.getProduct().getStock()){
            return "Quantity bigger than stock";
        }
        cartProduct.setQuantity(quantity);
        cartProductRepository.save(cartProduct);
        return "Success update";
    }
}

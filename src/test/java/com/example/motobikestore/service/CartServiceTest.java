package com.example.motobikestore.service;

import com.example.motobikestore.entity.CartProduct;
import com.example.motobikestore.entity.Product;
import com.example.motobikestore.entity.Users;
import com.example.motobikestore.repository.CartProductRepository;
import com.example.motobikestore.repository.ProductRepository;
import com.example.motobikestore.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private UsersRepository usersRepository;

    @MockBean
    private CartProductRepository cartProductRepository;

//    @Test
//    public void testAddItem2Cart() {
//        Long productID = 2652L;
//        String email = "admin@gmail.com";
//        Integer quantity = 2;
//
//        Product product = new Product();
//        when(productRepository.findById(eq(productID))).thenReturn(Optional.of(product));
//
//        Users user = new Users();
//        when(usersRepository.findByEmail(eq(email))).thenReturn(Optional.of(user));
//
//        String result = cartService.addItem2Cart(productID, email, quantity);
//
//        verify(cartProductRepository).save(any(CartProduct.class));
//        assertEquals("Added to cart", result);
//    }

//    @Test
//    public void testRemoveItem2Cart() {
//        Long productID = 2652L;
//        String email = "admin@gmail.com";
//
//        CartProduct cartProduct = new CartProduct();
//        when(cartProductRepository.findByCartProductIDAndUsers(eq(productID), eq(email))).thenReturn(cartProduct);
//
//        String result = cartService.removeItem2Cart(productID, email);
//
//        verify(cartProductRepository).delete(eq(cartProduct));
//        assertEquals("Remove to cart", result);
//    }

//    @Test
//    public void testUpdateQuantity() {
//        Long productID = 2652L;
//        String email = "admin@gmail.com";
//        Integer newQuantity = 5;
//
//        CartProduct cartProduct = new CartProduct();
//        when(cartProductRepository.findByCartProductIDAndUsers(eq(productID), eq(email))).thenReturn(cartProduct);
//
//        String result = cartService.updateQuantity(productID, email, newQuantity);
//
//        assertEquals(newQuantity, cartProduct.getQuantity());
//        verify(cartProductRepository).save(eq(cartProduct));
//        assertEquals("Update quantity", result);
//    }
}


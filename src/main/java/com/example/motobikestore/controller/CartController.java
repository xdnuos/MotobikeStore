package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.CartProductEdit;
import com.example.motobikestore.DTO.CartProductRequest;
import com.example.motobikestore.DTO.CartProductResponse;
import com.example.motobikestore.service.CartService;
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
@RequestMapping(API_V1_CART)
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping(GET_BY_ID)
    public List<CartProductResponse> getCart(@PathVariable UUID id){
        return cartService.getCartItem(id);
    }

    @PostMapping(ADD)
    public ResponseEntity<String> addItem2Cart(@RequestBody CartProductRequest cartProductRequest){
        return ResponseEntity.ok(cartService.addItem2Cart(cartProductRequest.getProductID(),cartProductRequest.getEmail(),cartProductRequest.getQuantity()));
    }
    @PutMapping(EDIT)
    public ResponseEntity<String> updateQuantity(@RequestBody CartProductEdit cartProductRequest){
        return ResponseEntity.ok(cartService.updateQuantity(cartProductRequest.getCartProductID(),cartProductRequest.getQuantity()));
    }
    @DeleteMapping(DELETE+"/{id}")
    public ResponseEntity<String> deleteItem2Cart(@PathVariable Long id){
        return ResponseEntity.ok(cartService.removeItem2Cart(id));
    }
}

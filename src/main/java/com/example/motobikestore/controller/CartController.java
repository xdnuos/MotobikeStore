package com.example.motobikestore.controller;

import com.example.motobikestore.DTO.CartProductEdit;
import com.example.motobikestore.DTO.CartProductRequest;
import com.example.motobikestore.DTO.CartProductResponse;
import com.example.motobikestore.service.CartService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public ResponseEntity<Map<String, Object>> addItem2Cart(@RequestBody CartProductRequest cartProductRequest){
        String message = cartService.addItem2Cart(cartProductRequest.getProductID(),cartProductRequest.getUserID(),cartProductRequest.getQuantity());
        return getMapResponseEntity(message, cartProductRequest.getUserID());
    }
    @PutMapping(EDIT)
    public ResponseEntity<Map<String, Object>> updateQuantity(@RequestBody CartProductEdit cartProductRequest){
        String message = cartService.updateQuantity(cartProductRequest.getCartProductID(),cartProductRequest.getQuantity());
        System.out.println("update cart ne");
        System.out.println(cartProductRequest.getUserID());
        return getMapResponseEntity(message, cartProductRequest.getUserID());
    }

    private ResponseEntity<Map<String, Object>> getMapResponseEntity(String message, UUID userID) {
        List<CartProductResponse> cartProductResponses = getCart(userID);
        Map<String, Object> data = new HashMap<>();
        data.put("message",message);
        data.put("cart",cartProductResponses);
        return ResponseEntity.ok(data);
    }

    @PutMapping(DELETE)
    public ResponseEntity<Map<String, Object>> deleteItem2Cart(@RequestBody CartRemoveItem cartRemoveItem){
        String message = cartService.removeItem2Cart(cartRemoveItem.getCartProductID());
        return getMapResponseEntity(message, cartRemoveItem.getUserID());
    }
    @AllArgsConstructor
    @Getter
    private class CartRemoveItem {
        Long cartProductID;
        UUID userID;
    }
}

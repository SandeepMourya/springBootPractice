package com.example.test.controller;

import com.example.test.dto.CartDTO;
import com.example.test.model.Cart;
import com.example.test.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestHeader("X-user-id") String userId, CartDTO cartItem){
        Boolean result = cartService.addToCart(userId, cartItem);
        if(result == true){
            return ResponseEntity.ok().body("Done !");
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @DeleteMapping("/deleteFromCart")
    public ResponseEntity<String> deleteFromCart(@RequestHeader("X-user-id") String userId, Long productID){
        Boolean result = cartService.deleteFromCart(userId, productID);
        if(result == true){
            return ResponseEntity.ok().body("Done Delete!");
        }
        return ResponseEntity.badRequest().body("Error Delete");
    }

    @GetMapping("/getCart")
    public ResponseEntity<List<Cart>> getCart(@RequestHeader("X-user-id") String userId){
        List<Cart> result = cartService.getCart(userId);
        return ResponseEntity.ok(result);
    }

}

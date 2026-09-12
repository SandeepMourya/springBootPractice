package com.example.test.service;

import com.example.test.dto.CartDTO;
import com.example.test.model.Cart;
import com.example.test.model.Product;
import com.example.test.model.User;
import com.example.test.repository.CartRepository;
import com.example.test.repository.ProductRepository;
import com.example.test.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    public Boolean addToCart(String userId, CartDTO request) {

        Optional<Product> pOpt = productRepository.findById(request.getProductID());
        if(pOpt.isEmpty())return false;

        Product product = pOpt.get();
        if(product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> uOpt = userRepository.findById(Long.valueOf(userId));
        if(uOpt.isEmpty())return false;

        User user = uOpt.get();

        Cart existingCart = cartRepository.findByUserAndProduct(user, product);

        if(existingCart != null){
            existingCart.setQuantity(existingCart.getQuantity() + request.getQuantity());
            existingCart.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCart.getQuantity())));
            cartRepository.save(existingCart);
        }else{
            Cart newCartItem = new Cart();
            newCartItem.setUser(user);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            newCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartRepository.save(newCartItem);
        }

        return  true;

    }

    @Transactional
    public Boolean deleteFromCart(String userId, Long productID) {

        Optional<Product> pOpt = productRepository.findById(productID);
        if(pOpt.isEmpty())return false;
        Product product = pOpt.get();

        Optional<User> uOpt = userRepository.findById(Long.valueOf(userId));
        if(uOpt.isEmpty())return false;
        User user = uOpt.get();

        cartRepository.deleteByUserAndProduct(user, product);

        return  true;

    }

    public List<Cart> getCart(String userId) {

        Optional<User> uOpt = userRepository.findById(Long.valueOf(userId));
        if(uOpt.isEmpty())return new ArrayList<>();
        User user = uOpt.get();
        return cartRepository.findByUser(user);

    }
}

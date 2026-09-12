package com.example.test.repository;

import com.example.test.model.Cart;
import com.example.test.model.Product;
import com.example.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<Cart> findByUser(User user);
}

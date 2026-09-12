package com.example.test.repository;

import com.example.test.model.Product;
import io.micrometer.common.KeyValues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByIsActiveTrue();

    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Optional<List<Product>> findProductByName(@Param("name") String name);
}

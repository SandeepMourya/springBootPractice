package com.example.test.controller;


import com.example.test.dto.ProductReqDTO;
import com.example.test.dto.ProductRespDTO;
import com.example.test.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productServiceservice;

    ProductController(ProductService service){
        this.productServiceservice = service;
    }

    @PostMapping("/createProduct")
    public ResponseEntity<ProductRespDTO> createProduct(@RequestBody ProductReqDTO product){
        return ResponseEntity.ok(productServiceservice.createProduct(product));
    }

    @GetMapping("/getAllProduct")
    public ResponseEntity<List<ProductRespDTO>> createProduct(){
        return ResponseEntity.ok(productServiceservice.getAllProduct());
    }

    @GetMapping("/getProductByName")
    public ResponseEntity<List<ProductRespDTO>> getProductBy(@RequestParam String name){
        return ResponseEntity.ok(productServiceservice.fndProductByName(name));
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductRespDTO> updateProduct(@RequestBody ProductReqDTO product, @RequestParam Long id){
        return ResponseEntity.ok(productServiceservice.updateProduct(product, id));
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Void> updateProduct(@RequestParam Long id){
        boolean deleted = productServiceservice.deleteProduct(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }


}

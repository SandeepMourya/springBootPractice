package com.example.test.service;

import com.example.test.dto.ProductReqDTO;
import com.example.test.dto.ProductRespDTO;
import com.example.test.model.Product;
import com.example.test.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository repo){
        this.productRepository = repo;
    }

    public ProductRespDTO createProduct(ProductReqDTO product) {

        Product p = ProductService.toProduct(product);
        productRepository.save(p);
        return ProductService.toProductRespDTO(p);

    }

    public ProductRespDTO updateProduct(ProductReqDTO product, Long id) {

        return productRepository.findById(id)
                .map(x -> {
                    x.setName(product.getName());
                    x.setDesc(product.getDesc());
                    x.setPrice(product.getPrice());
                    x.setCategory(product.getCategory());
                    x.setImageUrl(product.getImageUrl());
                    x.setStockQuantity(product.getStockQuantity());

                    Product updatedProduct = productRepository.save(x);

                    return ProductService.toProductRespDTO(updatedProduct);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public static Product toProduct(ProductReqDTO dto) {

        Product product = new Product();

        product.setName(dto.getName());
        product.setDesc(dto.getDesc());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        product.setImageUrl(dto.getImageUrl());
        product.setStockQuantity(dto.getStockQuantity());

        return product;
    }

    public static ProductRespDTO toProductRespDTO(Product product) {

        ProductRespDTO dto = new ProductRespDTO();

        dto.setId(String.valueOf(product.getId()));
        dto.setName(product.getName());
        dto.setDesc(product.getDesc());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setImageUrl(product.getImageUrl());
        dto.setStockQuantity(product.getStockQuantity());

        return dto;
    }


    public List<ProductRespDTO> getAllProduct() {

        return productRepository.findByIsActiveTrue()
                .stream().map(x->{
                    return toProductRespDTO(x);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(x->{
                    x.setActive(false);
                    productRepository.save(x);
                    return  true;
                }).orElse(false);
    }

    public List<ProductRespDTO> fndProductByName(String name){
        System.out.println(name + " ----------------------------------------");
        List<Product> p = productRepository.findProductByName(name).orElseThrow(()->new NoSuchElementException());
        return p.stream().map(x-> toProductRespDTO(x)).collect(Collectors.toUnmodifiableList());

    }
}

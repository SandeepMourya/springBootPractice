package com.example.test.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDTO {

    private Long ID;
    private Long productID;
    private Integer quantity;
    private BigDecimal price;

}

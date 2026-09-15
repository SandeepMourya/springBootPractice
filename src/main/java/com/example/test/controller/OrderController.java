package com.example.test.controller;

import com.example.test.dto.OrderResponseDTO;
import com.example.test.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    public final OrderService orderService;

    public ResponseEntity<OrderResponseDTO>createOrder(@RequestHeader("X-User-ID") String userID){
        Optional<OrderResponseDTO> order = orderService.createOrder(userID);
        if(order.isPresent())return new ResponseEntity<>(order.get(), HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }
}

package com.example.test.service;

import com.example.test.dto.OrderItemsDTO;
import com.example.test.dto.OrderResponseDTO;
import com.example.test.model.*;
import com.example.test.repository.OrderRepository;
import com.example.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartService cartService;
    private final UserRepository userRepository;

    public Optional<OrderResponseDTO> createOrder(String userID) {
        //validate for cart items
        //validate for user

        List<Cart> cart = cartService.getCart(userID);
        if(cart.isEmpty()){
            return  Optional.empty();
        }

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userID));
        if(userOptional.isEmpty()){
            return  Optional.empty();
        }

        User user = userOptional.get();

        BigDecimal total = cart.stream()
                .map(x-> x.getPrice())
                .reduce(BigDecimal.valueOf(0), (x, y)->x.add(y));

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CONFRMED);
        order.setTotalAmount(total);

        List<OrderItem>orderItems = cart.stream().map(x->{
           OrderItem item = new OrderItem();
           item.setOrder(order);
           item.setProduct(x.getProduct());
           item.setPrice(x.getPrice());
           item.setQuantity(x.getQuantity());
           return  item;
        }).toList();

        order.setOrderItemList(orderItems);

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(userID);


        return  Optional.ofNullable(mapToOrderResponse(order));
    }

    private OrderResponseDTO mapToOrderResponse(Order order) {
        OrderResponseDTO orderResp = new OrderResponseDTO();

        orderResp.setOrderItems(
            order.getOrderItemList().stream().map(x->{
                OrderItemsDTO orderItemResp = new OrderItemsDTO();
                orderItemResp.setPrice(x.getPrice());
                orderItemResp.setQuantity(x.getQuantity());
                orderItemResp.setProductID(x.getProduct().getId());
                return  orderItemResp;
            }).toList()
        );

        orderResp.setOrderStatus(order.getOrderStatus());
        orderResp.setTotalAmount(order.getTotalAmount());
        orderResp.setCreatedAt(order.getCreatedAt());

        return orderResp;

    }
}

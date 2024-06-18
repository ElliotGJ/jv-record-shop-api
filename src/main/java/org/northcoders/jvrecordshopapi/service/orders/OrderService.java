package org.northcoders.jvrecordshopapi.service.orders;

import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.shop.OrderDto;
import org.northcoders.jvrecordshopapi.repository.orders.OrderRepository;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    AccountService accountService;

    public HashSet<OrderDto> getAllOrdersDto(Long accountId) {
        HashSet<OrderDto> orderDtos = new HashSet<>();
        accountService.getAccountById(accountId).getOrders().forEach(order -> orderDtos.add(mapper.toOrderDto(order)));
        return orderDtos;
    }

    public OrderDto getOrderByIdDto(Long accountId, Long orderId) {
        return mapper.toOrderDto(orderRepository.findByAccount_IdAndId(accountId, orderId).orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId)));
    }
}

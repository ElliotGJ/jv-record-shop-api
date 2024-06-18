package org.northcoders.jvrecordshopapi.service.orders;

import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.shop.OrderDto;
import org.northcoders.jvrecordshopapi.exception.NotInStockException;
import org.northcoders.jvrecordshopapi.model.shop.Account;
import org.northcoders.jvrecordshopapi.model.shop.Basket;
import org.northcoders.jvrecordshopapi.model.shop.Order;
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

    @Autowired
    AddressService addressService;

    public HashSet<OrderDto> getAllOrdersDto(Long accountId) {
        HashSet<OrderDto> orderDtos = new HashSet<>();
        accountService.getAccountById(accountId).getOrders().forEach(order -> orderDtos.add(mapper.toOrderDto(order)));
        return orderDtos;
    }

    public OrderDto getOrderByIdDto(Long accountId, Long orderId) {
        return mapper.toOrderDto(orderRepository.findByAccount_IdAndId(accountId, orderId).orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId)));
    }

    public OrderDto createOrder(Long accountId, Long addressId) {
        Account account = accountService.getAccountById(accountId);
        Basket basket = account.getBasket().iterator().next();

        if (basket.getItems().isEmpty()) {
            throw new RuntimeException("Basket empty");
        }

        basket.getItems().forEach(basketItem -> {
            if (basketItem.getRecord().getStock().getStock() < basketItem.getQuantity()) {
            throw new NotInStockException("ID: " + basketItem.getRecord().getId() + " Name: " + basketItem.getRecord().getName() + " only has: " + basketItem.getRecord().getStock().getStock() + " in stock.");
        }
            basketItem.getRecord().getStock().setStock(basketItem.getRecord().getStock().getStock() - basketItem.getQuantity());
        });
        Order order = orderRepository.save(new Order(null, null, account, addressService.getAddressById(accountId, addressId), basket));
        basket.setOrdered(true);
        accountService.newBasket(accountId);
        return mapper.toOrderDto(order);
    }
}

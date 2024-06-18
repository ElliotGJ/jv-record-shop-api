package org.northcoders.jvrecordshopapi.controller.shop;

import org.northcoders.jvrecordshopapi.dto.shop.OrderDto;
import org.northcoders.jvrecordshopapi.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("api/account/{accountId}/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<HashSet<OrderDto>> getAllOrders(@PathVariable long accountId) {
        return new ResponseEntity<>(orderService.getAllOrdersDto(accountId), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable long accountId, @PathVariable long orderId) {
        return new ResponseEntity<>(orderService.getOrderByIdDto(accountId, orderId), HttpStatus.OK);
    }

    @PostMapping(params = {"addressId"})
    public ResponseEntity<OrderDto> createOrder(@PathVariable long accountId, @RequestParam long addressId) {
        return new ResponseEntity<>(orderService.createOrder(accountId, addressId), HttpStatus.CREATED);
    }
}

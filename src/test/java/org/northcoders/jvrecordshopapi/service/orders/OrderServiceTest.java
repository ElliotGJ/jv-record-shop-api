package org.northcoders.jvrecordshopapi.service.orders;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.dto.shop.OrderDto;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.shop.*;
import org.northcoders.jvrecordshopapi.repository.orders.OrderRepository;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class OrderServiceTest {

    @Spy
    Mapper mapper;

    @Mock
    OrderRepository orderRepository;


    @InjectMocks
    OrderService orderService;

    Order order;

    @BeforeEach
    void setUp() {
        order = new Order(1L, Timestamp.valueOf("2022-01-01 00:00:00"), new Account(1L, "James", "Jimbo", "yep@yep.com", null, null, null, null),
                new Address(1L, "James", "19", null, "Readin", "tq51je", null), new Basket(1L, new HashSet<>(), new Account(), true));
        order.getBasket().setItems(new HashSet<>(List.of(new BasketItem(1L, new Record(), 1, order.getBasket()))));
    }

    @Test
    void getAllOrdersDto() {
        when(orderRepository.findAllByAccount_Id(1L)).thenReturn(new ArrayList<>(List.of(order)));

        HashSet<OrderDto> result = orderService.getAllOrdersDto(1L);

        assertEquals(1, result.size());
        OrderDto orderDto = result.iterator().next();
        assertEquals(1L, orderDto.id());
        assertEquals(1L, orderDto.accountId());
        assertEquals(1, orderDto.items().size());

    }

    @Test
    void getOrderByIdDto() {
        when(orderRepository.findByAccount_IdAndId(1L, 1L)).thenReturn(Optional.ofNullable(order));

        OrderDto result = orderService.getOrderByIdDto(1L, 1L);

        assertEquals(1L, result.id());
        assertEquals(1L, result.accountId());
        assertEquals(1, result.items().size());
    }

    @Test
    void getOrderByIdDtoThrowsEntityNotFoundException() {
        when(orderRepository.findByAccount_IdAndId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.getOrderByIdDto(1L, 1L));
    }
}
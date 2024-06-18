package org.northcoders.jvrecordshopapi.controller.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import org.northcoders.jvrecordshopapi.dto.shop.BasketItemDto;
import org.northcoders.jvrecordshopapi.dto.shop.OrderDto;
import org.northcoders.jvrecordshopapi.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {



    @MockBean
    OrderService orderService;

    @Autowired
    OrderController orderController;

    @Autowired
    private MockMvc mockOrderController;

    @BeforeEach
    void setUp() {
        mockOrderController = MockMvcBuilders.standaloneSetup(orderController).build();
    }


    @Test
    void getAllOrders() throws Exception {
        given(orderService.getAllOrdersDto(1L)).willReturn(new HashSet<>(List.of
                (new OrderDto(1L, 1L, new ArrayList<>(List.of(new BasketItemDto(1L, "record", 1))),
                        "Yep", null, "Bh1ajf", "Bournemotuth", Timestamp.valueOf("2022-01-01 00:00:00")))));


        mockOrderController.perform(get("/api/account/1/orders").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].accountId", is(1)))
                .andExpect(jsonPath("$[0].items[0].recordId", is(1)));

    }

    @Test
    void getOrderById() throws Exception {
        given(orderService.getOrderByIdDto(1L, 1L)).willReturn(new OrderDto(1L, 1L, new ArrayList<>(List.of(new BasketItemDto(1L, "record", 1))),
                "Yep", null, "Bh1ajf", "Bournemotuth", Timestamp.valueOf("2022-01-01 00:00:00")));

        mockOrderController.perform(get("/api/account/1/orders/1").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.accountId", is(1)))
                .andExpect(jsonPath("$.items[0].recordId", is(1)));
    }
}
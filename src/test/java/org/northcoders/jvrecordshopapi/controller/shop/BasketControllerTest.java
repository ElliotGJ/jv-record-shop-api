package org.northcoders.jvrecordshopapi.controller.shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.dto.shop.BasketDto;
import org.northcoders.jvrecordshopapi.dto.shop.BasketItemDto;
import org.northcoders.jvrecordshopapi.service.orders.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class BasketControllerTest {

    @MockBean
    BasketService basketService;

    @Autowired
    BasketController basketController;

    @Autowired
    private MockMvc mockBasketController;

    @BeforeEach
    void setUp() {
        mockBasketController = MockMvcBuilders.standaloneSetup(basketController).build();
    }

    @Test
    void getBasket() throws Exception {
        when(basketService.getBasketDto(1L)).thenReturn(new BasketDto(new ArrayList<>(List.of(new BasketItemDto(1L, "record", 1)))));

        mockBasketController.perform(get("/api/account/1/basket").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].recordId").value(1));

    }
}
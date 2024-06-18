package org.northcoders.jvrecordshopapi.service.orders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.dto.shop.BasketDto;
import org.northcoders.jvrecordshopapi.exception.NotInStockException;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.records.Stock;
import org.northcoders.jvrecordshopapi.model.shop.Account;
import org.northcoders.jvrecordshopapi.model.shop.Basket;
import org.northcoders.jvrecordshopapi.repository.orders.BasketRepository;
import org.northcoders.jvrecordshopapi.service.records.RecordService;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.HashSet;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class BasketServiceTest {

    @Spy
    Mapper mapper;

    @Mock
    BasketRepository basketRepository;

    @Mock
    AccountService accountService;

    @Mock
    RecordService recordService;

    @InjectMocks
    BasketService basketService;


    Basket basket;
    Account account;
    @BeforeEach
    void setUp() {
        basket = new Basket(1L, new HashSet<>(), new Account(), false);
        account = new Account(1L, null, null, null, null, null, basket, null);
        basket.setAccount(account);

    }

    @Test
    void getBasketFromAccountIdDto() {
        when(basketRepository.findByAccount_Id(1L)).thenReturn(Optional.of(new Basket(1L, new HashSet<>(), new Account(), true)));

        assertNotNull(basketService.getBasketDto(1L));
    }

    @Test
    void getBasketFromAccountId() {
        when(basketRepository.findByAccount_Id(1L)).thenReturn(Optional.of(new Basket(1L, new HashSet<>(), new Account(), true)));

        assertNotNull(basketService.getBasketFromAccountId(1L));
    }

    @Test
    void modifyBasketItem() {
        Record record = new Record(1L, "Record One", new HashSet<>(), Year.of(2020), new HashSet<>(), new Stock());
        record.getStock().setStock(10);
        when(recordService.getRecordById(1L)).thenReturn(record);
        when(basketRepository.save(basket)).thenReturn(basket);
        when(accountService.getAccountById(1L)).thenReturn(account);

        BasketDto basketDto = basketService.modifyBasketItem(1L, 1L, 1);
        assertEquals(1, basketDto.items().size());
        assertEquals(record.getName(), basketDto.items().get(0).recordName());
    }

    @Test
    void modifyBasketItemWithZeroQuantity() {
        Record record = new Record(1L, "Record One", new HashSet<>(), Year.of(2020), new HashSet<>(), new Stock());
        record.getStock().setStock(10);
        when(recordService.getRecordById(1L)).thenReturn(record);
        when(basketRepository.save(basket)).thenReturn(basket);
        when(accountService.getAccountById(1L)).thenReturn(account);

        BasketDto basketDto = basketService.modifyBasketItem(1L, 1L, 1);
        assertEquals(1, basketDto.items().size());
        assertEquals(record.getName(), basketDto.items().get(0).recordName());

        basketDto = basketService.modifyBasketItem(1L, 1L, 0);
        assertEquals(0, basketDto.items().size());
    }


    @Test
    void modifyBasketItemWithNotEnoughStock() {
        Record record = new Record(1L, "Record One", new HashSet<>(), Year.of(2020), new HashSet<>(), new Stock());
        record.getStock().setStock(10);
        when(recordService.getRecordById(1L)).thenReturn(record);
        when(basketRepository.save(basket)).thenReturn(basket);
        when(accountService.getAccountById(1L)).thenReturn(account);

        BasketDto basketDto = basketService.modifyBasketItem(1L, 1L, 1);
        assertEquals(1, basketDto.items().size());
        assertEquals(record.getName(), basketDto.items().get(0).recordName());

        assertThrows(NotInStockException.class, () -> basketService.modifyBasketItem(1L, 1L, 11));
    }
}
package org.northcoders.jvrecordshopapi.service.orders;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.model.shop.Account;
import org.northcoders.jvrecordshopapi.model.shop.Basket;
import org.northcoders.jvrecordshopapi.repository.orders.AccountRepository;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class AccountServiceTest {

    @Spy
    Mapper mapper;

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    void getAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(new Account(1L, "John", "John", "John@John.com", null, null, new HashSet<>(List.of(new Basket())), new HashSet<>())));

        assertEquals(1L, accountService.getAccountById(1L).getId());
        assertEquals("John", accountService.getAccountById(1L).getFirstName());
    }
}
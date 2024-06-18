package org.northcoders.jvrecordshopapi.controller.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.dto.shop.AccountDto;
import org.northcoders.jvrecordshopapi.dto.shop.TempAccountCreationDto;
import org.northcoders.jvrecordshopapi.service.orders.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AccountControllerTest {

    @MockBean
    AccountService accountService;

    @Autowired
    AccountController accountController;

    @Autowired
    private MockMvc mockAccountController;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mockAccountController = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void getAccount() throws Exception {
        when(accountService.getAccountByIdDto(1L)).thenReturn(new AccountDto(1L, "John",
                "John", "John@John.com"));

        mockAccountController.perform(get("/api/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("John")))
                .andExpect(jsonPath("$.email", is("John@John.com")));
    }

    @Test
    void createAccount() throws Exception {
        TempAccountCreationDto tempAccountCreationDto = new TempAccountCreationDto("John", "John", "John@John.com");
        when(accountService.createAccount(tempAccountCreationDto)).thenReturn(new AccountDto(1L, "John", "John", "John@John.com"));

        mockAccountController.perform(put("/api/account").contentType("application/json").content(mapper.writeValueAsString(tempAccountCreationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("John")))
                .andExpect(jsonPath("$.email", is("John@John.com")));
    }
}
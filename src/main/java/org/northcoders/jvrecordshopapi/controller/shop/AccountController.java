package org.northcoders.jvrecordshopapi.controller.shop;

import org.northcoders.jvrecordshopapi.dto.shop.AccountDto;
import org.northcoders.jvrecordshopapi.dto.shop.TempAccountCreationDto;
import org.northcoders.jvrecordshopapi.service.orders.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable long accountId) {
        return new ResponseEntity<>(accountService.getAccountByIdDto(accountId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody TempAccountCreationDto accountCreationDto) {
        return new ResponseEntity<>(accountService.createAccount(accountCreationDto), HttpStatus.CREATED);
    }
}

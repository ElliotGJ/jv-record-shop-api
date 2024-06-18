package org.northcoders.jvrecordshopapi.service.orders;

import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.shop.AccountDto;
import org.northcoders.jvrecordshopapi.dto.shop.TempAccountCreationDto;
import org.northcoders.jvrecordshopapi.model.shop.Account;
import org.northcoders.jvrecordshopapi.repository.orders.AccountRepository;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    Mapper mapper;

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new EntityNotFoundException("Account not found"));
    }

    public AccountDto getAccountByIdDto(Long accountId) {
        return mapper.toAccountDto(getAccountById(accountId));
    }

    public AccountDto createAccount(TempAccountCreationDto accountCreationDto) {
        Account account = mapper.accountCreationDtoToAccount(accountCreationDto);
        return mapper.toAccountDto(accountRepository.save(account));
    }

}
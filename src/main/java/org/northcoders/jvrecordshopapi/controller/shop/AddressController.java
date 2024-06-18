package org.northcoders.jvrecordshopapi.controller.shop;


import org.northcoders.jvrecordshopapi.dto.shop.AddressDto;
import org.northcoders.jvrecordshopapi.service.orders.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account/{accountId}/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@PathVariable long accountId, @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.createAddress(accountId, addressDto), HttpStatus.CREATED);
    }
}

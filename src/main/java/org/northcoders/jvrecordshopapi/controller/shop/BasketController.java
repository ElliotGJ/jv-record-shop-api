package org.northcoders.jvrecordshopapi.controller.shop;

import org.northcoders.jvrecordshopapi.dto.shop.BasketDto;
import org.northcoders.jvrecordshopapi.service.orders.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/{accountId}/basket")
public class BasketController {

    @Autowired
    BasketService basketService;

    @GetMapping
    public ResponseEntity<BasketDto> getBasket(@PathVariable long accountId) {
        return new ResponseEntity<>(basketService.getBasketDto(accountId), HttpStatus.OK);
    }

    @PatchMapping("/{recordId}?quantity={quantity}")
    public ResponseEntity<BasketDto> modifyBasketItem(@PathVariable long accountId, @PathVariable long recordId, @RequestParam(defaultValue = "1", required = false) int quantity) {
        return new ResponseEntity<>(basketService.modifyBasketItem(accountId, recordId, quantity), HttpStatus.OK);
    }

}
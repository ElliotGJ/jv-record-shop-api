package org.northcoders.jvrecordshopapi.service.orders;

import org.northcoders.jvrecordshopapi.dto.shop.BasketDto;
import org.northcoders.jvrecordshopapi.exception.NotInStockException;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.shop.Basket;
import org.northcoders.jvrecordshopapi.model.shop.BasketItem;
import org.northcoders.jvrecordshopapi.repository.orders.BasketRepository;
import org.northcoders.jvrecordshopapi.service.records.RecordService;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketService {

    @Autowired
    Mapper mapper;

    @Autowired
    RecordService recordService;

    @Autowired
    AccountService accountService;

    @Autowired
    BasketRepository basketRepository;

    public BasketDto getBasketDto(Long accountId) {
        return mapper.toBasketDto(getBasketFromAccountId(accountId));
    }

    public Basket getBasketFromAccountId(Long accountId) {
        return accountService.getAccountById(accountId).getBasket().iterator().next();
    }

    public BasketDto modifyBasketItem(Long accountId, Long recordId, int quantity) {
        Record record = recordService.getRecordById(recordId);
        Basket basket = getBasketFromAccountId(accountId);

        if (quantity == 0) {
            basket.getItems().removeIf(basketItem -> basketItem.getRecord().equals(record));
        }

        else if (record.getStock().getStock() < quantity) {
            throw new NotInStockException("Record ID: " + recordId + " only has: " + record.getStock().getStock() + " in stock");
        }

        else if (basket.getItems().isEmpty()) {
            BasketItem basketItem = new BasketItem(null, record, quantity, basket);
            basket.getItems().add(basketItem);
        }


        else {
            basket.getItems().stream().filter(basketItem -> basketItem.getRecord()
                    .equals(record)).findFirst().ifPresentOrElse(basketItem -> basketItem.setQuantity(quantity), () -> {
                BasketItem basketItem = new BasketItem(null, record, quantity, basket);
                basket.getItems().add(basketItem);
            });
        }

        return mapper.toBasketDto(basketRepository.save(basket));

    }
}

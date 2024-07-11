package org.northcoders.jvrecordshopapi.repository.orders;

import org.northcoders.jvrecordshopapi.model.shop.Basket;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BasketRepository extends CrudRepository<Basket, Long> {
    Optional<Basket> findByAccount_Id(Long accountId);
}

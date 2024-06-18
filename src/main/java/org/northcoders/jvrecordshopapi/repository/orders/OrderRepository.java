package org.northcoders.jvrecordshopapi.repository.orders;

import org.northcoders.jvrecordshopapi.model.shop.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findAllByAccount_Id(Long accountId);
    Optional<Order> findByAccount_IdAndId(Long accountId, Long orderId);

}


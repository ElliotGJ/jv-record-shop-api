package org.northcoders.jvrecordshopapi.repository.orders;

import org.northcoders.jvrecordshopapi.model.shop.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findByAccount_IdAndId(Long accountId, Long addressId);
}

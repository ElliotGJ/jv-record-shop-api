package org.northcoders.jvrecordshopapi.service.orders;

import org.northcoders.jvrecordshopapi.dto.shop.AddressDto;
import org.northcoders.jvrecordshopapi.model.shop.Address;
import org.northcoders.jvrecordshopapi.repository.orders.AddressRepository;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressService {


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    Mapper mapper;

    public HashSet<AddressDto> getAllAddressesFromAccountIdDto(Long accountId) {
        return getAllAddressesFromAccountId(accountId).stream().map(mapper::toAddressDto).collect(Collectors.toCollection(HashSet::new));
    }

    public Set<Address> getAllAddressesFromAccountId(Long accountId) {
        return accountService.getAccountById(accountId).getAddresses();
    }

    public Address getAddressById(Long accountId, Long addressId) {
        return addressRepository.findByAccount_IdAndId(accountId, addressId).orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));
    }

    public AddressDto getAddressByIdDto(Long accountId, Long addressId) {
        return mapper.toAddressDto(getAddressById(accountId, addressId));
    }

    public Address deleteAddress(Long accountId, Long addressId) {
        Address address = getAddressById(accountId, addressId);
        addressRepository.delete(address);
        return address;
    }

    public AddressDto deleteAddressDto(Long accountId, Long addressId) {
        return mapper.toAddressDto(deleteAddress(accountId, addressId));
    }

    public AddressDto createAddress(Long accountId, AddressDto addressDto) {
        Address address = mapper.addressDtoToAddress(addressDto);
        System.out.println(addressDto.line2());
        address.setAccount(accountService.getAccountById(accountId));
        return mapper.toAddressDto(addressRepository.save(address));
    }

}

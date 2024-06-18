package org.northcoders.jvrecordshopapi.utils;

import org.northcoders.jvrecordshopapi.dto.records.*;
import org.northcoders.jvrecordshopapi.dto.shop.*;
import org.northcoders.jvrecordshopapi.model.records.Artist;
import org.northcoders.jvrecordshopapi.model.records.Genre;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.records.Stock;
import org.northcoders.jvrecordshopapi.model.shop.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class Mapper {

    public RecordDto toRecordDto(Record record) {
        return new RecordDto(
                record.getId(),
                record.getName(),
                record.getArtists().stream().map(Artist::getName).toList(),
                record.getReleaseYear(),
                record.getGenres().stream().map(Genre::getName).toList(),
                record.getStock().getStock());
    }

    public Record creationDtoToRecord(RecordCreationDto creationDto) {
        return new Record(
                null,
                creationDto.name(),
                new HashSet<>(),
                creationDto.releaseYear(),
                new HashSet<>(),
                new Stock());
    }

    public ArtistDto toArtistDto(Artist artist) {
        return new ArtistDto(
                artist.getId(),
                artist.getName(),
                artist.getRecords().stream().map(this::toRecordDto).toList()
        );
    }

    public ArtistDtoNoRecords toArtistDtoNoRecords(Artist artist) {
        return new ArtistDtoNoRecords(
                artist.getId(),
                artist.getName()
        );
    }

    public Artist artistFromName(String name) {
        return new Artist(null, name, new HashSet<>());
    }

    public GenreDto toGenreDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }

    public BasketItemDto toBasketItemDto(BasketItem basketItem) {
        return new BasketItemDto(
                basketItem.getRecord().getId(),
                basketItem.getRecord().getName(),
                basketItem.getQuantity()
        );
    }

    public OrderDto toOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getAccount().getId(),
                order.getBasket().getItems().stream().map(this::toBasketItemDto).toList(),
                order.getAddress().getLine1(),
                order.getAddress().getLine2(),
                order.getAddress().getCity(),
                order.getAddress().getPostcode(),
                order.getOrderDate()
        );
    }

    public BasketDto toBasketDto(Basket basket) {
        return new BasketDto(
                basket.getItems().stream().map(this::toBasketItemDto).toList()
        );
    }

    public AccountDto toAccountDto(Account account) {
        return new AccountDto(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail()
        );
    }

    public Account accountCreationDtoToAccount(TempAccountCreationDto accountCreationDto) {
        return new Account(
                null,
                accountCreationDto.firstName(),
                accountCreationDto.lastName(),
                accountCreationDto.email(),
                null,
                new HashSet<>(),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public AddressDto toAddressDto(Address address) {
        return new AddressDto(
                address.getId(),
                address.getName(),
                address.getLine1(),
                address.getLine2(),
                address.getCity(),
                address.getPostcode()
        );
    }

    public Address addressDtoToAddress(AddressDto addressDto) {
        return new Address(
                null,
                addressDto.name(),
                addressDto.line1(),
                addressDto.line2(),
                addressDto.city(),
                addressDto.postcode(),
                null
        );
    }

}
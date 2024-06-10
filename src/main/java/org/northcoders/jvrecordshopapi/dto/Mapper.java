package org.northcoders.jvrecordshopapi.dto;

import org.northcoders.jvrecordshopapi.model.Artist;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.model.Stock;
import org.springframework.stereotype.Component;

import java.util.HashSet;

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
                null);
    }
}
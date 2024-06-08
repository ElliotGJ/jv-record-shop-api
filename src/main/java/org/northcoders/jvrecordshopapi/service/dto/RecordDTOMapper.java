package org.northcoders.jvrecordshopapi.service.dto;

import org.northcoders.jvrecordshopapi.model.Artist;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Component
public class RecordDTOMapper implements Function<Record, RecordDTO> {

    @Override
    public RecordDTO apply(Record record) {
        return new RecordDTO(
                record.getId(),
                record.getName(),
                record.getArtists().stream().map(Artist::getName).toList(),
                record.getReleaseYear(),
                record.getGenres().stream().map(Genre::getName).toList(),
                record.getStock().getStock());
    }
}
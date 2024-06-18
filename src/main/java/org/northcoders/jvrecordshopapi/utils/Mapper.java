package org.northcoders.jvrecordshopapi.utils;

import org.northcoders.jvrecordshopapi.dto.records.*;
import org.northcoders.jvrecordshopapi.model.records.Artist;
import org.northcoders.jvrecordshopapi.model.records.Genre;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.records.Stock;
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
}
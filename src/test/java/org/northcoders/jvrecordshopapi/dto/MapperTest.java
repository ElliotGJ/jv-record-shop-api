package org.northcoders.jvrecordshopapi.dto;

import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.model.Artist;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
class MapperTest {


    @Autowired
    Mapper mapper;

    @Test
    void toRecordDto() throws Exception{
        Record record1 = new Record(1L, "Record One", new HashSet<>(),
                Year.of(2022), new HashSet<>(), new Stock());

        Genre genre = new Genre(1L, "Metal", new HashSet<>(List.of(record1)));
        record1.setGenres(new HashSet<>(List.of(genre)));
        Artist artist = new Artist(1L, "Lewis", new HashSet<>(List.of(record1)));
        record1.setArtists(new HashSet<>(List.of(artist)));

        System.out.println(mapper.toRecordDto(record1));
    }
}
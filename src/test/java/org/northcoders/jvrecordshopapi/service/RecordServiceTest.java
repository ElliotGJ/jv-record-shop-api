package org.northcoders.jvrecordshopapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.model.Stock;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class RecordServiceTest {

    private List<Record> testRecords;

    private Genre metal;
    private HashSet<Genre> genres;

    @Mock
    private RecordRepository recordRepository;
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    RecordService recordService;

    @BeforeEach
    void setup() {

        testRecords = new ArrayList<>();
        metal = new Genre(1L, "Metal", new HashSet<>());
        genres = new HashSet<>(List.of(metal));
        Record record1 = new Record(1L, "Record One", new ArrayList<>(),
                Year.of(2022), new HashSet<>(), new Stock());

        Record record2 = new Record(2L, "Record Two", new ArrayList<>(),
                Year.of(2016), genres, new Stock());

        Record record3 = new Record(3L, "Record Three", new ArrayList<>(),
                Year.of(2020), genres, new Stock());
        metal.setRecords(new HashSet<>(Arrays.asList(record1, record2)));
    }



    @Test
    @DisplayName("Return all records")
    void getAllRecordsTest() {
        given(recordRepository.findAll()).willReturn(testRecords);
        List<Record> results = recordService.getAllRecords();
        assertThat(results.size()).isEqualTo(testRecords.size());
        assertThat(results).isEqualTo(testRecords);
    }

    @Test
    @DisplayName("Return all records in genre")
    void getAllRecordsInGenreTest() {
        given(genreRepository.findByName("Metal")).willReturn(metal);
        List<Record> results = recordService.getAllRecordsInGenre("Metal");
        assertThat(results.size()).isEqualTo(metal.getRecords().size());
        assertThat(results.get(0).getGenres()).isEqualTo(genres);
    }
}
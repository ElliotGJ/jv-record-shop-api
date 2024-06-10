package org.northcoders.jvrecordshopapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.model.Stock;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.northcoders.jvrecordshopapi.dto.RecordDto;
import org.northcoders.jvrecordshopapi.dto.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class RecordServiceTest {

    private List<RecordDto> testRecordDtos;
    private List<Record> testRecords;
    private HashSet<Genre> genres;
    Genre metal;

    @Spy
    private Mapper mapper;


    @Mock
    private RecordRepository recordRepository;

    @Mock
    private GenreService genreService;

    @InjectMocks
    RecordService recordService;


    @BeforeEach
    void setup() {
        testRecords = new ArrayList<>();
        metal = new Genre(1L, "Metal", new HashSet<>());
        genres = new HashSet<>(List.of(metal));
        Record record1 = new Record(1L, "Record One", new HashSet<>(),
                Year.of(2022), new HashSet<>(), new Stock());

        Record record2 = new Record(2L, "Record Two", new HashSet<>(),
                Year.of(2016), genres, new Stock());

        Record record3 = new Record(3L, "Record Three", new HashSet<>(),
                Year.of(2020), genres, new Stock());
        metal.setRecords(new HashSet<>(Arrays.asList(record2, record3)));
        testRecords.add(record1);
        testRecords.add(record2);
        testRecords.add(record3);

        testRecordDtos = new ArrayList<>();
        RecordDto recordDto1 = new RecordDto(1L, "Record One", new ArrayList<>(List.of()),
                Year.of(2022), new ArrayList<>(List.of("")), 0);
        testRecordDtos.add(recordDto1);

        RecordDto recordDto2 = new RecordDto(2L, "Record Two", new ArrayList<>(List.of()),
                Year.of(2016), new ArrayList<>(List.of("Metal")), 0);
        testRecordDtos.add(recordDto2);

        RecordDto recordDto3 = new RecordDto(3L, "Record Three", new ArrayList<>(List.of()),
                Year.of(2020), new ArrayList<>(List.of("Metal")), 0);
        testRecordDtos.add(recordDto3);
    }


    @Test
    @DisplayName("Return all records")
    void getAllRecordsTest() {
        given(recordRepository.findAll()).willReturn(testRecords);
        List<RecordDto> results = recordService.getAllRecords();
        assertThat(results.size()).isEqualTo(testRecordDtos.size());
        assertThat(results.toString()).isEqualTo(testRecordDtos.toString());
    }

    @Test
    @DisplayName("Return all records in genre")
    void getAllRecordsInGenreTest() {
        given(genreService.getGenreByName("Metal")).willReturn(metal);
        List<RecordDto> results = recordService.getAllRecordsInGenre("Metal");
        assertThat(results.size()).isEqualTo(metal.getRecords().size());
        assertThat(results.get(0).genres()).isEqualTo(genres.stream().map(Genre::getName).toList());
    }


    @Test
    @DisplayName("Return record by id")
    void getRecordByIdTest() {
        given(recordRepository.findById(2L)).willReturn(Optional.ofNullable(testRecords.get(1)));
        RecordDto result = recordService.getRecordById(2L);
        assertThat(result).isEqualTo(testRecordDtos.get(1));
    }

    @Test
    @DisplayName("Throw error with wrong id")
    void throwByInvalidIdTest() {
        given(recordRepository.findById(5L)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> recordService.getRecordById(5L));
    }


}
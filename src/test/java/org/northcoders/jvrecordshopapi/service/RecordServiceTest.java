package org.northcoders.jvrecordshopapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.exception.ItemNotFoundException;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.model.Stock;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.northcoders.jvrecordshopapi.dto.RecordDTO;
import org.northcoders.jvrecordshopapi.dto.RecordDTOMapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class RecordServiceTest {

    private List<RecordDTO> testRecordDTOs;
    private List<Record> testRecords;
    private HashSet<Genre> genres;
    Genre metal;

    @Spy
    private RecordDTOMapper recordDTOMapper;


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

        testRecordDTOs = new ArrayList<>();
        RecordDTO recordDto1 = new RecordDTO(1L, "Record One", new ArrayList<>(List.of()),
                Year.of(2022), new ArrayList<>(List.of("")), 0);
        testRecordDTOs.add(recordDto1);

        RecordDTO recordDto2 = new RecordDTO(2L, "Record Two", new ArrayList<>(List.of()),
                Year.of(2016), new ArrayList<>(List.of("Metal")), 0);
        testRecordDTOs.add(recordDto2);

        RecordDTO recordDto3 = new RecordDTO(3L, "Record Three", new ArrayList<>(List.of()),
                Year.of(2020), new ArrayList<>(List.of("Metal")), 0);
        testRecordDTOs.add(recordDto3);
    }


    @Test
    @DisplayName("Return all records")
    void getAllRecordsTest() {
        given(recordRepository.findAll()).willReturn(testRecords);
        List<RecordDTO> results = recordService.getAllRecords();
        assertThat(results.size()).isEqualTo(testRecordDTOs.size());
        assertThat(results.toString()).isEqualTo(testRecordDTOs.toString());
    }

    @Test
    @DisplayName("Return all records in genre")
    void getAllRecordsInGenreTest() {
        given(genreRepository.findByName("Metal")).willReturn(Optional.ofNullable(metal));
        List<RecordDTO> results = recordService.getAllRecordsInGenre("Metal");
        assertThat(results.size()).isEqualTo(metal.getRecords().size());
        assertThat(results.get(0).genres()).isEqualTo(genres.stream().map(Genre::getName).toList());
    }

    @Test
    @DisplayName("Throw error with invalid genre")
    void throwByInvalidGenreTest() {
        given(genreRepository.findByName("Meetal")).willReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> recordService.getAllRecordsInGenre("Meetal"));
    }
    @Test
    @DisplayName("Return record by id")
    void getRecordByIdTest() {
        given(recordRepository.findById(2L)).willReturn(Optional.ofNullable(testRecords.get(1)));
        RecordDTO result = recordService.getRecordById(2L);
        assertThat(result).isEqualTo(testRecordDTOs.get(1));
    }

    @Test
    @DisplayName("Throw error with wrong id")
    void throwByInvalidIdTest() {
        given(recordRepository.findById(5L)).willReturn(Optional.empty());
        assertThrows(ItemNotFoundException.class, () -> recordService.getRecordById(5L));
    }


}
package org.northcoders.jvrecordshopapi.service.records;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.dto.records.RecordCreationDto;
import org.northcoders.jvrecordshopapi.model.records.Genre;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.records.Stock;
import org.northcoders.jvrecordshopapi.repository.records.RecordRepository;
import org.northcoders.jvrecordshopapi.dto.records.RecordDto;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    void getAllRecordsTest() throws Exception {
        given(recordRepository.findAll()).willReturn(testRecords);
        HashSet<RecordDto> results = recordService.getAllRecords();
        assertThat(results.size()).isEqualTo(testRecordDtos.size());
    }

    @Test
    @DisplayName("Return all records in genre")
    void getAllRecordsInGenreTest() throws Exception {
        given(genreService.getGenreByName("Metal")).willReturn(metal);
        HashSet<RecordDto> results = recordService.getAllRecordsInGenre("Metal");
        assertThat(results.size()).isEqualTo(metal.getRecords().size());
        assertThat(results.iterator().next().genres()).isEqualTo(genres.stream().map(Genre::getName).toList());
    }


    @Test
    @DisplayName("Return record by id")
    void getRecordByIdTest() throws Exception {
        given(recordRepository.findById(2L)).willReturn(Optional.ofNullable(testRecords.get(1)));
        RecordDto result = recordService.getRecordById(2L);
        assertThat(result).isEqualTo(testRecordDtos.get(1));
    }

    @Test
    @DisplayName("Throw error with wrong id")
    void throwByInvalidIdTest() throws Exception {
        given(recordRepository.findById(5L)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> recordService.getRecordById(5L));
    }

    @Test
    @DisplayName("Deletes record from id")
    void deleteRecordByIdTest() throws Exception {
        given(recordRepository.findById(1L)).willReturn(Optional.of(testRecords.get(0)));
        given(recordRepository.findById(2L)).willReturn(Optional.empty());

        recordService.deleteRecordById(1L);
        assertThrows(EntityNotFoundException.class, () -> recordService.deleteRecordById(2L));
    }

    @Test
    @DisplayName("Get records by name")
    void getRecordsByNameTest() throws Exception {
        given(recordRepository.findAllByNameIgnoreCase("Record Two")).willReturn(List.of(testRecords.get(1)));
        HashSet<RecordDto> results = recordService.getRecordsByName("Record Two");
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.iterator().next().name()).isEqualTo("Record Two");
    }

    @Test
    @DisplayName("Get records by release year")
    void getRecordsByReleaseYearTest() throws Exception {
        given(recordRepository.findAllByReleaseYear(Year.of(2020))).willReturn(List.of(testRecords.get(2)));
        HashSet<RecordDto> results = recordService.getRecordsByReleaseYear(Year.of(2020));
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.iterator().next().name()).isEqualTo("Record Three");
    }

    @Test
    @DisplayName("Get records in stock")
    void getRecordsInStockTest() throws Exception {
        given(recordRepository.findAllByStock_StockGreaterThan(0)).willReturn(List.of(testRecords.get(1), testRecords.get(2)));
        HashSet<RecordDto> results = recordService.getRecordsInStock(true);
        assertThat(results.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Update record")
    void updateRecordTest() throws Exception {
        Record record = testRecords.get(1);
        RecordDto recordDto = testRecordDtos.get(1);
        RecordDto updatedRecordDto = new RecordDto(2L, "Record Two", new ArrayList<>(),
                Year.of(2016), new ArrayList<>(List.of("Metal")), 0);
        given(recordRepository.findById(2L)).willReturn(Optional.of(record));
        given(genreService.getGenresFromName(new ArrayList<>(List.of("Metal")))).willReturn(genres);
        given(genreService.getGenreByName("Metal")).willReturn(metal);
        given(recordRepository.save(record)).willReturn(record);
        given(mapper.toRecordDto(record)).willReturn(updatedRecordDto);
        RecordDto result = recordService.updateRecord(2L, new RecordCreationDto("Record Two", null,
                Year.of(2016), new ArrayList<>(List.of("Metal")), 0));
        assertThat(result).isEqualTo(updatedRecordDto);
    }
}

package org.northcoders.jvrecordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.model.Stock;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class RecordControllerTest {

    @MockBean
    private RecordService recordService;

    @Autowired
    private RecordController recordController;

    @Autowired
    private MockMvc mockRecordController;

    private ObjectMapper mapper;

    private List<Record> testRecords;
    private Genre metal;
    private HashSet<Genre> genres;
    private List<Record> genreExpected;

    @BeforeEach
    public void setup() {
        mockRecordController = MockMvcBuilders.standaloneSetup(recordController).build();
        mapper = new ObjectMapper();
        testRecords = new ArrayList<>();
        metal = new Genre(1L, "Metal", new HashSet<>());
        genres = new HashSet<>(List.of(metal));
        Record record1 = new Record(1L, "Record One", new ArrayList<>(),
                Year.of(2022), new HashSet<>(), new Stock());

        Record record2 = new Record(2L, "Record Two", new ArrayList<>(),
                Year.of(2016), genres, new Stock());

        Record record3 = new Record(3L, "Record Three", new ArrayList<>(),
                Year.of(2020), genres, new Stock());
        metal.setRecords(new HashSet<>(Arrays.asList(record2, record3)));
        testRecords.add(record1);
        testRecords.add(record2);
        testRecords.add(record3);

        genreExpected = new ArrayList<>(List.of(record2, record3));
    }

    @Test
    @DisplayName("Get /records")
    void getAllRecords() throws Exception {

        given(recordService.getAllRecords()).willReturn(testRecords);

        mockRecordController.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(testRecords.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(testRecords.get(1).getName())));

    }

    @Test
    @DisplayName("Get /records by genre")
    void getAllRecordsByGenreTest() throws Exception {
        given(recordService.getAllRecordsInGenre("Metal")).willReturn(genreExpected);

        mockRecordController.perform(get("/api/records/Metal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(genreExpected.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(genreExpected.get(1).getName())));
    }
}
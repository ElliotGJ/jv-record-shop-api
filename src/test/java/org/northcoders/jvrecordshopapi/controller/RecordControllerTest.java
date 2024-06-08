package org.northcoders.jvrecordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.northcoders.jvrecordshopapi.service.dto.RecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
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
    private List<RecordDTO> testRecordDTOs;
    private List<RecordDTO> testRecordMetalDTOs;

    @BeforeEach
    public void setup() {
        mockRecordController = MockMvcBuilders.standaloneSetup(recordController).build();
        mapper = new ObjectMapper();
        testRecordDTOs = new ArrayList<>();
        RecordDTO recordDto1 = new RecordDTO(1L, "Record One", new ArrayList<>(List.of()),
                Year.of(2022), new ArrayList<>(List.of("")), 0);
        testRecordDTOs.add(recordDto1);
        testRecordMetalDTOs = new ArrayList<>();
        RecordDTO recordDto2 = new RecordDTO(2L, "Record Two", new ArrayList<>(List.of()),
                Year.of(2016), new ArrayList<>(List.of("Metal")), 0);
        testRecordDTOs.add(recordDto2);
        testRecordMetalDTOs.add(recordDto2);

        RecordDTO recordDto3 = new RecordDTO(3L, "Record Three", new ArrayList<>(List.of()),
                Year.of(2020), new ArrayList<>(List.of("Metal")), 0);
        testRecordDTOs.add(recordDto3);
        testRecordMetalDTOs.add(recordDto3);

    }

    @Test
    @DisplayName("Get /records")
    void getAllRecords() throws Exception {

        given(recordService.getAllRecords()).willReturn(testRecordDTOs);

        mockRecordController.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(testRecordDTOs.get(0).name())))
                .andExpect(jsonPath("$[1].genres", is(testRecordDTOs.get(1).genres())));

    }

    @Test
    @DisplayName("Get /records by genre")
    void getAllRecordsByGenreTest() throws Exception {
        given(recordService.getAllRecordsInGenre("Metal")).willReturn(testRecordMetalDTOs);

        mockRecordController.perform(get("/api/records/Metal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testRecordMetalDTOs.get(0).name())))
                .andExpect(jsonPath("$[1].stock", is(testRecordMetalDTOs.get(1).stock())));
    }
}
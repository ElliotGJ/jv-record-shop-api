package org.northcoders.jvrecordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.northcoders.jvrecordshopapi.dto.RecordDto;
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
    private List<RecordDto> testRecordDtos;
    private List<RecordDto> testRecordMetalDTOs;

    @BeforeEach
    public void setup() {
        mockRecordController = MockMvcBuilders.standaloneSetup(recordController).build();
        mapper = new ObjectMapper();
        testRecordDtos = new ArrayList<>();
        RecordDto recordDto1 = new RecordDto(1L, "Record One", new ArrayList<>(List.of()),
                Year.of(2022), new ArrayList<>(List.of("")), 0);
        testRecordDtos.add(recordDto1);
        testRecordMetalDTOs = new ArrayList<>();
        RecordDto recordDto2 = new RecordDto(2L, "Record Two", new ArrayList<>(List.of()),
                Year.of(2016), new ArrayList<>(List.of("Metal")), 0);
        testRecordDtos.add(recordDto2);
        testRecordMetalDTOs.add(recordDto2);

        RecordDto recordDto3 = new RecordDto(3L, "Record Three", new ArrayList<>(List.of()),
                Year.of(2020), new ArrayList<>(List.of("Metal")), 0);
        testRecordDtos.add(recordDto3);
        testRecordMetalDTOs.add(recordDto3);

    }

    @Test
    @DisplayName("Get /record")
    void getAllRecords() throws Exception {

        given(recordService.getAllRecords()).willReturn(testRecordDtos);

        mockRecordController.perform(get("/api/record")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(testRecordDtos.get(0).name())))
                .andExpect(jsonPath("$[1].genres", is(testRecordDtos.get(1).genres())));
    }


    @Test
    @DisplayName("Get /record by genre")
    void getAllRecordsByGenreTest() throws Exception {
        given(recordService.getAllRecordsInGenre("Metal")).willReturn(testRecordMetalDTOs);

        mockRecordController.perform(get("/api/record?genre=Metal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testRecordMetalDTOs.get(0).name())))
                .andExpect(jsonPath("$[1].stock", is(testRecordMetalDTOs.get(1).stock())));
    }

    @Test
    @DisplayName("Get /record by id")
    void getRecordById() throws Exception {
        given(recordService.getRecordById(2L)).willReturn(testRecordDtos.get(1));

        mockRecordController.perform(get("/api/record/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testRecordDtos.get(1).name())))
                .andExpect(jsonPath("$.stock", is(testRecordDtos.get(1).stock())));
    }

//    @Test
//    @DisplayName("Add new /record")
//    void addNewRecordTest() throws Exception {
//        RecordDto testRecord = new RecordDto(null, "Record", )
//        given(recordService.addNewRecord()).willReturn();
//    }

}
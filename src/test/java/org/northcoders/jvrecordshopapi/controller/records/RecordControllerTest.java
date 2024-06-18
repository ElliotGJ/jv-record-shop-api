package org.northcoders.jvrecordshopapi.controller.records;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.dto.records.RecordCreationDto;
import org.northcoders.jvrecordshopapi.service.records.RecordService;
import org.northcoders.jvrecordshopapi.dto.records.RecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
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
    private HashSet<RecordDto> testRecordDtos;
    private HashSet<RecordDto> testRecordMetalDTOs;

    @BeforeEach
    public void setup() {
        mockRecordController = MockMvcBuilders.standaloneSetup(recordController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        testRecordDtos = new HashSet<>();
        RecordDto recordDto1 = new RecordDto(1L, "Record One", new ArrayList<>(List.of()),
                Year.of(2022), new ArrayList<>(List.of("")), 0);
        testRecordDtos.add(recordDto1);
        testRecordMetalDTOs = new HashSet<>();
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

        given(recordService.getAllRecordsDto()).willReturn(testRecordDtos);

        mockRecordController.perform(get("/api/record")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(testRecordDtos.iterator().next().name())))
                .andExpect(jsonPath("$[1].genres", is(testRecordDtos.iterator().next().genres())));
    }


    @Test
    @DisplayName("Get /record by genre")
    void getAllRecordsByGenreTest() throws Exception {
        given(recordService.getAllRecordsInGenreDto("Metal")).willReturn(testRecordMetalDTOs);

        mockRecordController.perform(get("/api/record?genre=Metal")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testRecordMetalDTOs.iterator().next().name())))
                .andExpect(jsonPath("$[1].stock", is(testRecordMetalDTOs.iterator().next().stock())));
    }

    @Test
    @DisplayName("Get /record by id")
    void getRecordById() throws Exception {
        given(recordService.getRecordByIdDto(2L)).willReturn(testRecordDtos.iterator().next());

        mockRecordController.perform(get("/api/record/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(testRecordDtos.iterator().next().name())))
                .andExpect(jsonPath("$.stock", is(testRecordDtos.iterator().next().stock())));
    }

    @Test
    @DisplayName("Add new /record")
    void addNewRecordTest() throws Exception {
        Year year = Year.of(2020);
        RecordCreationDto testRecord = new RecordCreationDto("Cool Record", new ArrayList<Long>(List.of(1L)), year,
                new ArrayList<String>(List.of("Metal")), 50);
        RecordDto expectedReturn = new RecordDto(1L, "Cool Record", new ArrayList<String>(List.of("George")), year,
                new ArrayList<String>(List.of("Metal")), 50);
        given(recordService.addNewRecord(testRecord)).willReturn(expectedReturn);


        mockRecordController.perform(post("/api/record").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testRecord))).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Delete /record from id")
    void deleteRecordByIdTest() throws Exception {
        mockRecordController.perform(delete("/api/record/1")).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Get /record by year")
    void getAllRecordsByYearTest() throws Exception {
        given(recordService.getRecordsByReleaseYear(Year.of(2020))).willReturn(testRecordMetalDTOs);

        mockRecordController.perform(get("/api/record?year=2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testRecordMetalDTOs.iterator().next().name())))
                .andExpect(jsonPath("$[1].stock", is(testRecordMetalDTOs.iterator().next().stock())));
    }

    @Test
    @DisplayName("Get /record by name")
    void getRecordsByNameTest() throws Exception {
        given(recordService.getRecordsByName("Record Two")).willReturn(new HashSet<>(List.of(new RecordDto(1L, "Record Two",
                new ArrayList<>(List.of()), Year.of(2016), new ArrayList<>(List.of("Metal")), 0))));
        mockRecordController.perform(get("/api/record?name=Record Two"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Record Two")))
                .andExpect(jsonPath("$[0].stock", is(0)));
    }

    @Test
    @DisplayName("Get /record by inStock")
    void getAllRecordsInStockTest() throws Exception {
        given(recordService.getRecordsInStock(true)).willReturn(testRecordMetalDTOs);

        mockRecordController.perform(get("/api/record?inStock=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(testRecordMetalDTOs.iterator().next().name())))
                .andExpect(jsonPath("$[1].stock", is(testRecordMetalDTOs.iterator().next().stock())));
    }

    @Test
    @DisplayName("Update /record")
    void updateRecordTest() throws Exception {
        Year year = Year.of(2020);
        RecordCreationDto testRecord = new RecordCreationDto("Cool Record", new ArrayList<Long>(List.of(1L)), year,
                new ArrayList<String>(List.of("Metal")), 50);
        RecordDto expectedReturn = new RecordDto(1L, "Cool Record", new ArrayList<String>(List.of("George")), year,
                new ArrayList<String>(List.of("Metal")), 50);
        given(recordService.updateRecord(1L, testRecord)).willReturn(expectedReturn);

        mockRecordController.perform(put("/api/record/1").contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testRecord))).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
package org.northcoders.jvrecordshopapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class RecordControllerTest {

    @Mock
    private RecordService recordService;

    @Autowired
    RecordController recordController;

    @Autowired
    private MockMvc mockRecordController;

    private ObjectMapper mapper;

    private List<Record> testRecords;
    @BeforeEach
    public void setup() {
        mockRecordController = MockMvcBuilders.standaloneSetup(recordController).build();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        testRecords = new ArrayList<>();
        Record record1 = new Record(); record1.setName("Test record 1"); testRecords.add(record1);
        Record record2 = new Record(); record2.setName("Test record 2"); testRecords.add(record2);
        Record record3 = new Record(); record3.setName("Test record 3"); testRecords.add(record3);
    }

    @Test
    @DisplayName("Get /records")
    void getAllRecords() throws Exception{
        given(recordService.getAllRecords()).willReturn(testRecords);

        mockRecordController.perform(get("/api/records")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(testRecords.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(testRecords.get(1).getName())));

    }
}
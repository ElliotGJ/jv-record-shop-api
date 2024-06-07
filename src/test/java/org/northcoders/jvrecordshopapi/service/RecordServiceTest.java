package org.northcoders.jvrecordshopapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class RecordServiceTest {

    List<Record> testRecords;

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    RecordService recordService;

    @BeforeEach
    void setup() {
        testRecords = new ArrayList<>();
        Record record1 = new Record(); record1.setName("Test record 1"); testRecords.add(record1);
        Record record2 = new Record(); record2.setName("Test record 2"); testRecords.add(record2);
        Record record3 = new Record(); record3.setName("Test record 3"); testRecords.add(record3);
    }



    @Test
    void getAllRecords() {
        given(recordRepository.findAll()).willReturn(testRecords);
        List<Record> results = recordService.getAllRecords();
        assertThat(results.size()).isEqualTo(testRecords.size());
        assertThat(results).isEqualTo(testRecords);
    }
}
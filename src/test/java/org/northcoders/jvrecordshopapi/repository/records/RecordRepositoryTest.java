package org.northcoders.jvrecordshopapi.repository.records;

import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.model.records.Record;
import org.northcoders.jvrecordshopapi.model.records.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class RecordRepositoryTest {
    @Autowired
    RecordRepository recordRepository;

    @Test
    void findAllByStock_StockGreaterThan() {
        Record record = new Record(null, "Record One", new HashSet<>(), Year.of(2020), new HashSet<>(), new Stock());
        record.getStock().setStock(10);
        record.getStock().setRecord(record);
        Record record2 = new Record(null, "Record Two", new HashSet<>(), Year.of(2020), new HashSet<>(), new Stock());
        record2.getStock().setStock(5);
        record2.getStock().setRecord(record2);
        Record record3 = new Record(null, "Record Three", new HashSet<>(), Year.of(2020), new HashSet<>(), new Stock());
        record3.getStock().setStock(0);
        record3.getStock().setRecord(record3);
        recordRepository.save(record);
        recordRepository.save(record2);
        recordRepository.save(record3);

        Iterable<Record> records = recordRepository.findAllByStock_StockGreaterThan(0);
        assertEquals(2, ((List<Record>) records).size());
    }
}
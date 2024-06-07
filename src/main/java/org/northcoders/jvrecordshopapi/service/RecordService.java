package org.northcoders.jvrecordshopapi.service;

import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<>();
        recordRepository.findAll().forEach(records::add);
        return records;
    }
}

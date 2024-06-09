package org.northcoders.jvrecordshopapi.service;

import org.northcoders.jvrecordshopapi.exception.ItemNotFoundException;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.northcoders.jvrecordshopapi.dto.RecordDTO;
import org.northcoders.jvrecordshopapi.dto.RecordDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    RecordDTOMapper recordDTOMapper;

    @Autowired
    GenreRepository genreRepository;

    public List<RecordDTO> getAllRecords() {
        List<RecordDTO> recordDtos = new ArrayList<>();
        recordRepository.findAll().forEach(record -> recordDtos.add(recordDTOMapper.apply(record)));
        return recordDtos;
    }

    public List<RecordDTO> getAllRecordsInGenre(String genreName) {
        Optional<Genre> genre = genreRepository.findByName(genreName);
        if (genre.isEmpty()) {
            throw new ItemNotFoundException("Genre: " + genreName + " not found.");
        }
        List<RecordDTO> recordDtos = new ArrayList<>();
        genre.get().getRecords().forEach(record -> recordDtos.add(recordDTOMapper.apply(record)));
        return recordDtos;
    }

    public RecordDTO getRecordById(long id) {
        Optional<Record> result = recordRepository.findById(id);
        if (result.isEmpty()) {
            throw new ItemNotFoundException("Record: " + id + " not found.");
        }
        return recordDTOMapper.apply(result.get());
    }

}

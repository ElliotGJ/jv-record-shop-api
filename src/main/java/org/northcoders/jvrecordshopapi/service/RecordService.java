package org.northcoders.jvrecordshopapi.service;

import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.northcoders.jvrecordshopapi.service.dto.RecordDTO;
import org.northcoders.jvrecordshopapi.service.dto.RecordDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Genre genre = genreRepository.findByName(genreName);
        List<RecordDTO> recordDtos = new ArrayList<>();
        genre.getRecords().forEach(record -> recordDtos.add(recordDTOMapper.apply(record)));
        return recordDtos;
    }

}

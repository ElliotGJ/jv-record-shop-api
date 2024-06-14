package org.northcoders.jvrecordshopapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.RecordCreationDto;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.northcoders.jvrecordshopapi.dto.RecordDto;
import org.northcoders.jvrecordshopapi.dto.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    Mapper mapper;

    @Autowired
    ArtistService artistService;

    @Autowired
    GenreService genreService;

    public HashSet<RecordDto> getAllRecords() {
        HashSet<RecordDto> recordDtos = new HashSet<>();
        recordRepository.findAll().forEach(record -> recordDtos.add(mapper.toRecordDto(record)));
        return recordDtos;
    }

    public HashSet<RecordDto> getAllRecordsInGenre(String genreName) {
        HashSet<RecordDto> recordDtos = new HashSet<>();
        genreService.getGenreByName(genreName).getRecords().forEach(record -> recordDtos.add(mapper.toRecordDto(record)));
        return recordDtos;
    }

    public RecordDto getRecordById(long id) {
        return mapper.toRecordDto(recordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Record not found with ID: " + id)));
        }

    public RecordDto addNewRecord(RecordCreationDto creationDto) {
        Record record = mapper.creationDtoToRecord(creationDto);

        record.setArtists(artistService.getArtistsFromIds(creationDto.artistIds()));
        record.getArtists().forEach(artist -> artist.getRecords().add(record));

        record.setGenres(genreService.getGenresFromName(creationDto.genres()));
        record.getGenres().forEach(genre -> genre.getRecords().add(record));

        record.getStock().setRecord(record);
        record.getStock().setStock(creationDto.stock());

        Record savedRecord = recordRepository.save(record);

        return mapper.toRecordDto(savedRecord);
    }

    public void deleteRecordById(Long id) {
        if (recordRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Record: " + id + " not found.");
        } else {
            recordRepository.deleteById(id);
        }

    }

    public HashSet<RecordDto> getRecordsByName(String name) {
        HashSet<RecordDto> recordDtos = new HashSet<>();
        recordRepository.findAllByNameIgnoreCase(name).forEach(record -> recordDtos.add(mapper.toRecordDto(record)));
        return recordDtos;
    }

    public HashSet<RecordDto> getRecordsByReleaseYear(Year year) {
        HashSet<RecordDto> recordDtos = new HashSet<>();
        recordRepository.findAllByReleaseYear(year).forEach(record -> recordDtos.add(mapper.toRecordDto(record)));
        return recordDtos;
    }

}

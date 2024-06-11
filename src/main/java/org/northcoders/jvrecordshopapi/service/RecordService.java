package org.northcoders.jvrecordshopapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.RecordCreationDto;
import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.repository.RecordRepository;
import org.northcoders.jvrecordshopapi.dto.RecordDto;
import org.northcoders.jvrecordshopapi.dto.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<RecordDto> getAllRecords() {
        List<RecordDto> recordDtos = new ArrayList<>();
        recordRepository.findAll().forEach(record -> recordDtos.add(mapper.toRecordDto(record)));
        return recordDtos;
    }

    public List<RecordDto> getAllRecordsInGenre(String genreName) {
        List<RecordDto> recordDtos = new ArrayList<>();
        genreService.getGenreByName(genreName).getRecords().forEach(record -> recordDtos.add(mapper.toRecordDto(record)));
        return recordDtos;
    }

    public RecordDto getRecordById(long id) {
        Optional<Record> result = recordRepository.findById(id);
        if (result.isEmpty()) {
            throw new EntityNotFoundException("Record: " + id + " not found.");
        }
        return mapper.toRecordDto(result.get());
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

    public boolean deleteRecordById(Long id) {
        if (recordRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Record: " + id + " not found.");
        } else {
            recordRepository.deleteById(id);
            return true;
        }

    }

}

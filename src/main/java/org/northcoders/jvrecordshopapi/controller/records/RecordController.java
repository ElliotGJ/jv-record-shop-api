package org.northcoders.jvrecordshopapi.controller.records;


import org.northcoders.jvrecordshopapi.dto.records.RecordCreationDto;
import org.northcoders.jvrecordshopapi.service.records.RecordService;
import org.northcoders.jvrecordshopapi.dto.records.RecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.HashSet;


@RestController
@RequestMapping("api/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping
    public ResponseEntity<HashSet<RecordDto>> getAllRecords() {
        return new ResponseEntity<>(recordService.getAllRecords(), HttpStatus.OK);
    }

    @GetMapping(params = {"name"})
    public ResponseEntity<HashSet<RecordDto>> getAllRecordsByName(@RequestParam(required = false) String name) {
        return new ResponseEntity<>(recordService.getRecordsByName(name), HttpStatus.OK);
    }

    @GetMapping(params = {"genre"})
    public ResponseEntity<HashSet<RecordDto>> getAllRecordsByGenre(@RequestParam(required = false) String genre) {
        return new ResponseEntity<>(recordService.getAllRecordsInGenre(genre), HttpStatus.OK);
    }

    @GetMapping(params = {"year"})
    public ResponseEntity<HashSet<RecordDto>> getAllRecordsByYear(@RequestParam(required = false) Year year) {
        return new ResponseEntity<>(recordService.getRecordsByReleaseYear(year), HttpStatus.OK);
    }

    @GetMapping(params = {"inStock"})
    public ResponseEntity<HashSet<RecordDto>> getAllRecordsInStock(@RequestParam(required = false) boolean inStock) {
        return new ResponseEntity<>(recordService.getRecordsInStock(inStock), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDto> getRecordById(@PathVariable long id) {
        return new ResponseEntity<>(recordService.getRecordById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecordDto> addNewRecord(@RequestBody @Validated RecordCreationDto creationDto) {
        return new ResponseEntity<>(recordService.addNewRecord(creationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable long id) {
        recordService.deleteRecordById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordDto> updateRecord(@PathVariable long id, @RequestBody RecordCreationDto creationDto) {
        return new ResponseEntity<>(recordService.updateRecord(id, creationDto), HttpStatus.OK);
    }
}

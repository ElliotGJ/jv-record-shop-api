package org.northcoders.jvrecordshopapi.controller;


import org.northcoders.jvrecordshopapi.dto.RecordCreationDto;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.northcoders.jvrecordshopapi.dto.RecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("api/record")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping
    public ResponseEntity<HashSet<RecordDto>> getAllRecords(@RequestParam(required = false) String genre) {
        if (genre != null) {
            return new ResponseEntity<>(recordService.getAllRecordsInGenre(genre), HttpStatus.OK);
        }
        return new ResponseEntity<>(recordService.getAllRecords(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDto> getRecordById(@PathVariable long id) {
        return new ResponseEntity<>(recordService.getRecordById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RecordDto> addNewRecord(@RequestBody RecordCreationDto creationDto) {
        return new ResponseEntity<>(recordService.addNewRecord(creationDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecord(@PathVariable long id) {
        recordService.deleteRecordById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

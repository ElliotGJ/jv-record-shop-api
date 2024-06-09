package org.northcoders.jvrecordshopapi.controller;


import org.northcoders.jvrecordshopapi.service.RecordService;
import org.northcoders.jvrecordshopapi.dto.RecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("api/records")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping
    public ResponseEntity<List<RecordDTO>> getAllRecords(@RequestParam(required = false) String genre) {
        if (Objects.isNull(genre)) {
            return new ResponseEntity<>(recordService.getAllRecords(), HttpStatus.OK);
        }
        return new ResponseEntity<>(recordService.getAllRecordsInGenre(genre), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordDTO> getRecordById(@PathVariable long id) {
        return new ResponseEntity<>(recordService.getRecordById(id), HttpStatus.OK);
    }

}

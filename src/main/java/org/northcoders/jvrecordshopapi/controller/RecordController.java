package org.northcoders.jvrecordshopapi.controller;


import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("api/records")
public class RecordController {

    @Autowired
    RecordService recordService;

//    @GetMapping
//    public ResponseEntity<List<Record>> getAllRecords() {
//        return new ResponseEntity<>(recordService.getAllRecords(), HttpStatus.OK);
//    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<Record>> getAllRecordsInGenre(@PathVariable String genre) {
        return new ResponseEntity<>(recordService.getAllRecordsInGenre(genre), HttpStatus.OK);
    }
}

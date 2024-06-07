package org.northcoders.jvrecordshopapi.controller;


import org.northcoders.jvrecordshopapi.model.Record;
import org.northcoders.jvrecordshopapi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("api/records")
public class RecordController {

    @Autowired
    RecordService recordService;

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        return new ResponseEntity<>(recordService.getAllRecords(), HttpStatus.OK);
    }
}

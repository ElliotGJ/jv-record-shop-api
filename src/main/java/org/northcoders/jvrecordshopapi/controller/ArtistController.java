package org.northcoders.jvrecordshopapi.controller;

import org.northcoders.jvrecordshopapi.dto.ArtistDto;
import org.northcoders.jvrecordshopapi.dto.ArtistDtoNoRecords;
import org.northcoders.jvrecordshopapi.model.Artist;
import org.northcoders.jvrecordshopapi.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("api/artist")
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping
    public ResponseEntity<HashSet<ArtistDtoNoRecords>> getAllArtists() {
        return new ResponseEntity<>(artistService.getAllArtists(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArtistDto> addNewArtist(@RequestBody String name) {
        return new ResponseEntity<>(artistService.addNewArtist(name), HttpStatus.CREATED);
    }

}

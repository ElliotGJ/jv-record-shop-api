package org.northcoders.jvrecordshopapi.controller.records;

import jakarta.validation.constraints.NotBlank;
import org.northcoders.jvrecordshopapi.dto.records.ArtistDto;
import org.northcoders.jvrecordshopapi.dto.records.ArtistDtoNoRecords;
import org.northcoders.jvrecordshopapi.service.records.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("api/artist")
public class ArtistController {
//
    @Autowired
    ArtistService artistService;

    @GetMapping
    public ResponseEntity<HashSet<ArtistDtoNoRecords>> getAllArtists(@RequestParam(required = false) String name) {
        if (name != null) {
            return new ResponseEntity<>(artistService.getArtistsByName(name), HttpStatus.OK);
        }
        return new ResponseEntity<>(artistService.getAllArtists(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ArtistDto> addNewArtist(@RequestBody @NotBlank(message = "Artist name should contain a value") String name) {
        return new ResponseEntity<>(artistService.addNewArtist(name), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable long id) {
        return new ResponseEntity<>(artistService.getArtistById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> updateArtist(@PathVariable long id, @RequestBody String name) {
        return new ResponseEntity<>(artistService.updateArtist(id, name), HttpStatus.OK);
    }

}

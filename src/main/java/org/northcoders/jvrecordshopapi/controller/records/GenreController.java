package org.northcoders.jvrecordshopapi.controller.records;


import org.northcoders.jvrecordshopapi.dto.records.GenreDto;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.northcoders.jvrecordshopapi.service.records.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("api/genre")
public class GenreController {

    @Autowired
    GenreService genreService;
    Mapper mapper;

    @PostMapping
    public ResponseEntity<GenreDto> addNewGenre(@RequestBody String name) {
        return new ResponseEntity<>(mapper.toGenreDto(genreService.addGenre(name)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable long id) {
        genreService.deleteGenreById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<HashSet<GenreDto>> getAllGenres() {
        return new ResponseEntity<>(genreService.getAllGenres(), HttpStatus.OK);
    }
}

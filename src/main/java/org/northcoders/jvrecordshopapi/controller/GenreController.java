package org.northcoders.jvrecordshopapi.controller;

import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/genre")
public class GenreController {

    @Autowired
    GenreService genreService;

    @PostMapping
    public ResponseEntity<Genre> addNewGenre(@RequestBody String name) {
        return new ResponseEntity<>(genreService.addGenre(name), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable long id) {
        genreService.deleteGenreById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package org.northcoders.jvrecordshopapi.service;


import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.exception.DuplicateEntryException;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;


    public Genre getGenreByName(String genreName) {
        Optional<Genre> genre = genreRepository.findByNameIgnoreCase(genreName);
        if (genre.isEmpty()) {
            throw new EntityNotFoundException("Genre: " + genreName + " not found.");
        }
        return genre.get();
    }

    public Genre addGenre(String name) {
        Genre genre = new Genre(null, name, new HashSet<>());
        if (genreRepository.findByNameIgnoreCase(name).isEmpty()) {
            return genreRepository.save(genre);
        }else throw new DuplicateEntryException("Genre: " + name + " already exists.");
    }

    public HashSet<Genre> getGenresFromName(List<String> genreNames) {
        HashSet<Genre> genres = new HashSet<>();
        genreRepository.findAllByNameInIgnoreCase(genreNames).forEach(genres::add);

        List<String> notFoundGenreNames = genreNames.stream()
                .filter(genreName -> !genres.stream().map(Genre::getName).toList().contains(genreName))
                .toList();
        if (!notFoundGenreNames.isEmpty()) {
            notFoundGenreNames.forEach(s -> genres.add(addGenre(s)));
        }
        return genres;
    }

}

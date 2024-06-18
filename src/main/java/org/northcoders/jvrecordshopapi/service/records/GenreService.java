package org.northcoders.jvrecordshopapi.service.records;


import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.records.GenreDto;
import org.northcoders.jvrecordshopapi.exception.DuplicateEntryException;
import org.northcoders.jvrecordshopapi.model.records.Genre;
import org.northcoders.jvrecordshopapi.repository.records.GenreRepository;
import org.northcoders.jvrecordshopapi.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    GenreRepository genreRepository;
    @Autowired
    private Mapper mapper;


    public Genre getGenreByName(String genreName) {
        Optional<Genre> genre = genreRepository.findByNameIgnoreCase(genreName);
        if (genre.isEmpty()) {
            throw new EntityNotFoundException("Genre: " + genreName + " not found.");
        }
        return genre.get();
    }

    public HashSet<GenreDto> getAllGenres() {
        HashSet<GenreDto> genreDtos = new HashSet<>();
        genreRepository.findAll().forEach(genre -> genreDtos.add(mapper.toGenreDto(genre)));
        return genreDtos;
    }

    public Genre addGenre(String name) {
        Genre genre = new Genre(null, name, new HashSet<>());
        if (genreRepository.findByNameIgnoreCase(name).isEmpty()) {
            return genreRepository.save(genre);
        }else throw new DuplicateEntryException("Genre: " + name + " already exists.");
    }

    public void deleteGenreById(long id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Genre not found with ID: " + id);
        }
    }

    public void deleteGenreByName(String name) {
        if (genreRepository.findByNameIgnoreCase(name).isPresent()) {
            genreRepository.deleteByNameIgnoreCase(name);
        } else {
            throw new EntityNotFoundException("Genre not found with name: " + name);
        }
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

package org.northcoders.jvrecordshopapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.northcoders.jvrecordshopapi.dto.GenreDto;
import org.northcoders.jvrecordshopapi.exception.DuplicateEntryException;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.northcoders.jvrecordshopapi.repository.GenreRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class GenreServiceTest {

    @Mock
    GenreRepository genreRepository;

    @InjectMocks
    GenreService genreService;

    @Test
    void getGenreByName() throws Exception {
        Genre metal = new Genre(1L, "Metal", null);
        given(genreRepository.findByNameIgnoreCase("Metal")).willReturn(Optional.of(metal));
        Genre result = genreService.getGenreByName("Metal");
        assertThat(result).isEqualTo(metal);
    }

    @Test
    @DisplayName("Throw error with invalid genre")
    void throwByInvalidGenreTest() throws Exception {
        given(genreRepository.findByNameIgnoreCase("Meetal")).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> genreService.getGenreByName("Meetal"));
    }

    @Test
    @DisplayName("Return genre with valid genre")
    void getGenresFromName() throws Exception {
        Genre metal = new Genre(1L, "Metal", null);
        Genre rock = new Genre(2L, "Rock", null);

        Iterable<Genre> iterable = new ArrayList<>(List.of(metal, rock));
        List<String> genreNames = new ArrayList<>(List.of("Metal", "Rock"));
        given(genreRepository.findAllByNameInIgnoreCase(genreNames)).willReturn(iterable);
        HashSet<Genre> results = genreService.getGenresFromName(genreNames);
        assertThat(results).hasSize(2);
        assertThat(results).containsAll(iterable);
    }

    @Test
    @DisplayName("Add valid genre")
    void addGenre() throws Exception {
        Genre metal = new Genre(1L, "Metal", new HashSet<>());
        given(genreRepository.findByNameIgnoreCase("Metal")).willReturn(Optional.empty());
        given(genreRepository.save(ArgumentMatchers.any(Genre.class))).willReturn(metal);
        Genre result = genreService.addGenre("Metal");
        assertThat(result).isEqualTo(metal);
    }

    @Test
    @DisplayName("Throw error with duplicate genre")
    void throwByDuplicateGenreTest() throws Exception {
        Genre metal = new Genre(1L, "Metal", null);
        given(genreRepository.findByNameIgnoreCase("Metal")).willReturn(Optional.of(metal));
        assertThrows(DuplicateEntryException.class, () -> genreService.addGenre("Metal"));
    }

    @Test
    @DisplayName("Get all genres")
    void getAllGenres() throws Exception {
        Genre metal = new Genre(1L, "Metal", null);
        Genre rock = new Genre(2L, "Rock", null);
        Iterable<Genre> iterable = new ArrayList<>(List.of(metal, rock));
        given(genreRepository.findAll()).willReturn(iterable);
        HashSet<GenreDto> results = genreService.getAllGenres();
        assertThat(results).hasSize(2);
    }
}
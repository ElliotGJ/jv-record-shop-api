package org.northcoders.jvrecordshopapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    void getGenreByName() {
    }

    @Test
    @DisplayName("Throw error with invalid genre")
    void throwByInvalidGenreTest() {
        given(genreRepository.findByName("Meetal")).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> genreService.getGenreByName("Meetal"));
    }

    @Test
    void getGenresFromName() {
        Genre metal = new Genre(1L, "Metal", null);
        Genre rock = new Genre(2L, "Rock", null);

        Iterable<Genre> iterable = new ArrayList<>(List.of(metal, rock));
        List<String> genreNames = new ArrayList<>(List.of("Metal", "Rock"));
        given(genreRepository.findAllByNameIn(genreNames)).willReturn(iterable);
        HashSet<Genre> results = genreService.getGenresFromName(genreNames);
        assertThat(results).hasSize(2);
        assertThat(results).containsAll(iterable);
    }
}
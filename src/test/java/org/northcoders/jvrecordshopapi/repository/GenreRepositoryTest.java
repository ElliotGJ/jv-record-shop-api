package org.northcoders.jvrecordshopapi.repository;

import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class GenreRepositoryTest {
    @Autowired
    GenreRepository genreRepository;


    @Test
    void findByNameTest() throws Exception{
        Genre genre = new Genre();
        genre.setName("Metal");
        genre = genreRepository.save(genre);
        Optional<Genre> result = genreRepository.findByName("Metal");
        assertThat(result.get()).isEqualTo(genre);
    }

    @Test
    void findAllByNameTest() throws Exception{
        Genre metal = new Genre();
        Genre rock = new Genre();
        metal.setName("Metal");
        rock.setName("Rock");
        metal = genreRepository.save(metal);
        metal = genreRepository.save(rock);
        Iterable<Genre> result = genreRepository.findAllByNameIn(List.of("Metal", "Rock"));
        assertThat(result).hasSize(2);
    }
}
package org.northcoders.jvrecordshopapi.repository;

import org.junit.jupiter.api.Test;
import org.northcoders.jvrecordshopapi.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class GenreRepositoryTest {
    @Autowired
    GenreRepository genreRepository;


    @Test
    void findByNameTest() {
        Genre genre = new Genre();
        genre.setName("Metal");
        genre = genreRepository.save(genre);
        Genre result = genreRepository.findByName("Metal");
        assertThat(result).isEqualTo(genre);
    }
}
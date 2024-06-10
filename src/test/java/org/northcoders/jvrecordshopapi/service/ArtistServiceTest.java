package org.northcoders.jvrecordshopapi.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.northcoders.jvrecordshopapi.dto.Mapper;
import org.northcoders.jvrecordshopapi.model.Artist;
import org.northcoders.jvrecordshopapi.repository.ArtistRepository;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;


@DataJpaTest
class ArtistServiceTest {


    @Mock
    ArtistRepository artistRepository;

    @Spy
    Mapper mapper;

    @InjectMocks
    ArtistService artistService;

    @Test
    void getArtistsFromIds() throws Exception{
        List<Long> artistIds = new ArrayList<>(List.of(1L, 2L));
        Artist artist = new Artist(1L, "James", null);
        Artist artistTwo = new Artist(2L, "James", null);
        Iterable<Artist> artists = new ArrayList<>(List.of(artist, artistTwo));
        given(artistRepository.findAllById(artistIds)).willReturn(artists);
        HashSet<Artist> results = artistService.getArtistsFromIds(artistIds);
        assertThat(results.size()).isEqualTo(2);
        assertTrue(results.contains(artistTwo));
        assertTrue(results.contains(artist));
    }

    @Test
    void getInvalidArtistId() throws Exception{
        List<Long> artistIds = new ArrayList<>(List.of(1L, 2L));
        Artist artist = new Artist(1L, "James", null);
        Artist artistTwo = new Artist(2L, "James", null);
        Iterable<Artist> artists = new ArrayList<>(List.of(artistTwo));
        given(artistRepository.findAllById(artistIds)).willReturn(artists);
        assertThrows(EntityNotFoundException.class, () -> artistService.getArtistsFromIds(artistIds));
    }
}
package org.northcoders.jvrecordshopapi.service;


import jakarta.persistence.EntityNotFoundException;
import org.northcoders.jvrecordshopapi.dto.ArtistDto;
import org.northcoders.jvrecordshopapi.dto.ArtistDtoNoRecords;
import org.northcoders.jvrecordshopapi.dto.Mapper;
import org.northcoders.jvrecordshopapi.model.Artist;
import org.northcoders.jvrecordshopapi.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    Mapper mapper;

    public HashSet<ArtistDtoNoRecords> getAllArtists() {
        HashSet<ArtistDtoNoRecords> artists = new HashSet<>();
        artistRepository.findAll().forEach(artist -> artists.add(mapper.toArtistDtoNoRecords(artist)));
        return artists;
    }

    public HashSet<Artist> getArtistsFromIds(List<Long> artistIds) {
        HashSet<Artist> artists = new HashSet<>();
        artistRepository.findAllById(artistIds).forEach(artists::add);

        List<Long> notFoundArtistIds = artistIds.stream()
                .filter(artistId -> !artists.stream().map(Artist::getId).toList().contains(artistId))
                .toList();
        if (!notFoundArtistIds.isEmpty()) {
            throw new EntityNotFoundException("Artists not found with IDs: " + notFoundArtistIds);
        }

        return artists;
    }

    public ArtistDto addNewArtist(String name) {
        return mapper.toArtistDto(artistRepository.save(mapper.artistFromName(name)));
    }

    public ArtistDto getArtistById(long id) {
        return mapper.toArtistDto(artistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Artist not found with ID: " + id)));
    }

    public HashSet<ArtistDtoNoRecords> getArtistsByName(String name) {
        HashSet<ArtistDtoNoRecords> artists = new HashSet<>();
        artistRepository.findArtistsByNameIgnoreCase(name).forEach(artist -> artists.add(mapper.toArtistDtoNoRecords(artist)));
        return artists;
    }

    public ArtistDto updateArtist(long id, String name) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Artist not found with ID: " + id));
        artist.setName(name);
        return mapper.toArtistDto(artistRepository.save(artist));
    }
}

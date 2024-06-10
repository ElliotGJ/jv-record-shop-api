package org.northcoders.jvrecordshopapi.service;


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

    public HashSet<Artist> getArtistsFromIds(List<Long> artistIds) {
        HashSet<Artist> artists = new HashSet<>();
        artistRepository.findAllById(artistIds).forEach(artists::add);
        return artists;
    }
}

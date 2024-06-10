package org.northcoders.jvrecordshopapi.repository;

import org.northcoders.jvrecordshopapi.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    Optional<Artist> findArtistByName(String name);

//    Iterable<Artist> findAllById(Iterable<Long> iterableIds);
}

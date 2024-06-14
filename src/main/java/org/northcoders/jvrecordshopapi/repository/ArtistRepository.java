package org.northcoders.jvrecordshopapi.repository;

import org.northcoders.jvrecordshopapi.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    HashSet<Artist> findArtistsByNameIgnoreCase(String name);


}

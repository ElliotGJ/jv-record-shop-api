package org.northcoders.jvrecordshopapi.repository.records;

import org.northcoders.jvrecordshopapi.model.records.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.HashSet;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
    HashSet<Artist> findArtistsByNameIgnoreCase(String name);
}

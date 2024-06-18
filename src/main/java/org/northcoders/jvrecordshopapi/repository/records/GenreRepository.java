package org.northcoders.jvrecordshopapi.repository.records;

import org.northcoders.jvrecordshopapi.model.records.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Optional<Genre> findByNameIgnoreCase(String name);
    Iterable<Genre> findAllByNameInIgnoreCase(Collection<String> name);
    void deleteByNameIgnoreCase(String name);
}

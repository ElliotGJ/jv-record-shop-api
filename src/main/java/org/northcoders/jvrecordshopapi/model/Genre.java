package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "Genres")
@Data
public class Genre {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(nullable = false, updatable=false)
    String genre;

    @ManyToMany
    @JoinTable(
            name="records_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    Set<Record> records;
}

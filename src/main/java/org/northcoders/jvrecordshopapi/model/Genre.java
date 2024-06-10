package org.northcoders.jvrecordshopapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Genres")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(unique = true, nullable = false, updatable=false)
    String name;


    @ManyToMany
    @JoinTable(
            name="records_genres",
            joinColumns = @JoinColumn(name = "genre_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    Set<Record> records;
}

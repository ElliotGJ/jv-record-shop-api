package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;
import java.util.Set;

@Entity
@Table(name = "Records")
@Data
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false)
    private Year releaseYear;

    @ManyToMany(mappedBy = "records")
    private Set<Genre> genres;

    @OneToOne
    private Stock stock;
}

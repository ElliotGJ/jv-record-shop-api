package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name="artists_records",
            joinColumns = @JoinColumn(name = "artist_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "record_id", nullable = false))
    private Set<Artist> artists;


    @Column(nullable = false)
    private Year releaseYear;

    @ManyToMany(mappedBy = "records")
    private Set<Genre> genres;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Stock stock;
}

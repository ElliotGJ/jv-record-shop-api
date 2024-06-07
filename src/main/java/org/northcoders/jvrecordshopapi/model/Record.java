package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Records")
@Data
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
    private List<Artist> artists;

    @Column(nullable = false)
    private Year releaseYear;

    @ManyToMany(mappedBy = "records")
    private Set<Genre> genres;

    @OneToOne
    private Stock stock;
}

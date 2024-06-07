package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "Artists")
@Data
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="artist", fetch = FetchType.LAZY)
    private Set<Record> records;
}

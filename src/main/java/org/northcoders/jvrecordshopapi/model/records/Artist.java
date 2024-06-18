package org.northcoders.jvrecordshopapi.model.records;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "Artists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "artists", fetch = FetchType.LAZY)
    private Set<Record> records;
}

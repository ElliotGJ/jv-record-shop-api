package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Stock")
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Record record;

    @Column(nullable = false)
    private int stock;
}

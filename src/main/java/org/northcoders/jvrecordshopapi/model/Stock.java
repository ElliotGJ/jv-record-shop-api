package org.northcoders.jvrecordshopapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stock {

    @Id
    @Column(name = "record_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "record_id")
    private Record record;

    @Column(nullable = false)
    private int stock;
}

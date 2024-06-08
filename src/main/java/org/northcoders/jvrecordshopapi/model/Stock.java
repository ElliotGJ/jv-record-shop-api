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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private int stock;
}

package org.northcoders.jvrecordshopapi.model.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.northcoders.jvrecordshopapi.model.records.Record;

@Entity
@Table(name = "BasketItems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "record_id", nullable = false, updatable = false)
    private Record record;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false, updatable = false)
    private Basket basket;
}

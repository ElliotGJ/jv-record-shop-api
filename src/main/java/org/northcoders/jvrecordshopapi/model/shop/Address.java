package org.northcoders.jvrecordshopapi.model.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Addresses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String line1;

    @Column
    private String line2;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String postcode;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;
}

package org.northcoders.jvrecordshopapi.model.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Baskets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY)
    private Set<BasketItem> items;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @Column(nullable = false)
    private boolean ordered;

}

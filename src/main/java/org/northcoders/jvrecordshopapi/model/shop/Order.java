package org.northcoders.jvrecordshopapi.model.shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private java.sql.Timestamp orderDate;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, updatable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false, updatable = false)
    private Basket basket;


}

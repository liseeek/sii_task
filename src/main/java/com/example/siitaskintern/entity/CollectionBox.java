package com.example.siitaskintern.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "collection_boxes")
public class CollectionBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal targetAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal currentAmount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "fundraising_event_id")
    private FundraisingEvent fundraisingEvent;

    private boolean isEmpty;
    private boolean isAssigned;

    public CollectionBox() {
        this.currentAmount = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.isEmpty = true;
        this.isAssigned = false;
    }

    public CollectionBox(String name, String description, BigDecimal targetAmount, String currency) {
        this();
        this.name = name;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currency = currency;
    }

}

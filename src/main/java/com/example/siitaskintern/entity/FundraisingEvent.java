package com.example.siitaskintern.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Getter
@Setter
public class FundraisingEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String currency;
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "fundraisingEvent")
    @JsonIgnore
    private List<CollectionBox> collectionBoxes;
}

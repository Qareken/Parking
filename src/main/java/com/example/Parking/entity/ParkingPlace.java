package com.example.Parking.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@ToString
public class ParkingPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingPlace", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PlaceLot> placeLot = new HashSet<>();
    private BigDecimal price;
    private BigDecimal priceTotal;
}

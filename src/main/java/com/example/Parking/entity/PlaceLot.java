package com.example.Parking.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@FieldNameConstants
public class PlaceLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameOfLot;

    @ManyToOne
    @JoinColumn(name = "parking_place_id", nullable = false)
    private ParkingPlace parkingPlace;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "placeLot", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Set<UnAvailableDates> unavailableDates;


}

package com.example.parking.data.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "vehicle")
@Table(name = "vehicle")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Vehicle {

    @Id
    private String vehicleId;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private String ownerName;
}

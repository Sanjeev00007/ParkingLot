package com.example.parking.data.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Data
@Entity(name = "ParkingSlot") //to identify any class as entity.
@Table(name = "parkingslot") //exact name of the table as in db
@NoArgsConstructor      //create empty constructor
@AllArgsConstructor     //create constructor with all the fields
@Builder                //Dynamically pass the fields
@NamedQuery(name = "getFreeSlot", query = "Select ps from ParkingSlot ps where ps.occupied = :occupied and ps.vehicleType = :vehicleType")
public class ParkingSlot {
    @Id
    @GeneratedValue(generator = "system.uuid")
    @GenericGenerator(name = "system.uuid", strategy = "uuid")
    private String id;
    @Enumerated(EnumType.STRING)  // for enum-type (ie vehicleType) so that instead of 0,1,2... it would call string by name like car, bike etc
    private VehicleType vehicleType;
    private boolean occupied;
    private String vehicleId;
}
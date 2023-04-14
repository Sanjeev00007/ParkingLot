package com.example.parking.data.requests;


import com.example.parking.data.entity.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSlotRequest {
    private VehicleType vehicleType;
}

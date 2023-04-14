package com.example.parking.data.dataStore;

import com.example.parking.data.entity.ParkingSlot;
import com.example.parking.data.entity.Vehicle;
import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class InMemoryDataStore {
    private Map<String, ParkingSlot> slotTable;
    private Map<String, Vehicle> vehicleTable;

    public InMemoryDataStore() {
        slotTable = new HashMap<>();
        vehicleTable = new HashMap<>();
    }

}

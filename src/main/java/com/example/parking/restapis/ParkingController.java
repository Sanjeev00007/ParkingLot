package com.example.parking.restapis;

import com.example.parking.data.entity.ParkingSlot;
import com.example.parking.data.entity.VehicleType;
import com.example.parking.data.requests.BookSlotRequest;
import com.example.parking.data.requests.CreateSlotRequest;
import com.example.parking.data.requests.ReleaseSlotRequest;
import com.example.parking.data.responses.BookSlotResponse;
import com.example.parking.data.responses.ReleaseSlotResponse;
import com.example.parking.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController                        //sort of register below-mentioned controllers
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @GetMapping("/getFreeSlots/{vehicleType}")
    @Transactional
    public List<ParkingSlot> getSlots(@PathVariable VehicleType vehicleType) {

        if (vehicleType == null) {
            return new ArrayList<>();
        }

            return parkingService.getAvailableSlotForVehicle(vehicleType);
    }

    @PutMapping("/createSlot")
    @Transactional
    public ParkingSlot createSlot(@RequestBody CreateSlotRequest createSlotRequest) {

      return parkingService.createSlot(createSlotRequest);
    }

    @PostMapping("/bookSlot")
    @Transactional
    public BookSlotResponse bookSlot(@RequestBody BookSlotRequest bookSlotRequest){
        return parkingService.bookSlot(bookSlotRequest);
    }

    @DeleteMapping("/releaseSlot")
    @Transactional
    public ReleaseSlotResponse releaseSlot(@RequestBody ReleaseSlotRequest releaseSlotRequest){
        return parkingService.releaseSlot(releaseSlotRequest);
    }

    @DeleteMapping("/deleteSlot/{slotId}")
    @Transactional
    public String DeleteEmptySlot(@PathVariable String slotId){
        return parkingService.deleteSlot(slotId);
    }


}

//server.port=7070 in app.properties
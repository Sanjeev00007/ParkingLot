package com.example.parking.service;

import com.example.parking.data.entity.ParkingSlot;
import com.example.parking.data.entity.Vehicle;
import com.example.parking.data.entity.VehicleType;
import com.example.parking.data.requests.BookSlotRequest;
import com.example.parking.data.requests.CreateSlotRequest;
import com.example.parking.data.requests.ReleaseSlotRequest;
import com.example.parking.data.responses.BookSlotResponse;
import com.example.parking.data.responses.ReleaseSlotResponse;
import com.example.parking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service     //@Service is specialized version of the @Component annotation.
public class ParkingService {

    @Autowired
    ParkingRepository parkingRepository;

    public List<ParkingSlot>  getAvailableSlotForVehicle(VehicleType vehicleType) {
        List<ParkingSlot> parkingSlots = parkingRepository.getFreeSlotForType2(vehicleType);
        return parkingSlots;
    }

    public ParkingSlot createSlot(CreateSlotRequest createSlotRequest) {
        ParkingSlot parkingSlot = ParkingSlot.builder()
                .id(UUID.randomUUID().toString())
                .vehicleType(createSlotRequest.getVehicleType())
                .occupied(false)
                .build();
        parkingRepository.createAndUpdateSlot(parkingSlot);
        return parkingSlot;
    }

    public String deleteSlot(String slotId){
        ParkingSlot parkingSlot = parkingRepository.getSlotById((slotId));
        if (parkingSlot.isOccupied()){
            return "Slot is occupied, you may delete once unoccupied";
        } else {
            parkingRepository.deleteSlot(slotId);
            return slotId + " is successfully deleted.";
        }
    }

    public ReleaseSlotResponse releaseSlot(ReleaseSlotRequest releaseSlotRequest){
        ParkingSlot parkingSlot = parkingRepository.getSlotById((releaseSlotRequest.getSlotId()));
        if (!parkingSlot.isOccupied()){
            return  ReleaseSlotResponse.builder()
                    .isSucc(false)
                    .mesg("Slot id already unoccupied")
                    .build();
        }

        parkingSlot.setOccupied(false);
        parkingSlot.setVehicleId(null);

        parkingRepository.releaseSlot(releaseSlotRequest.getVehicleId(), parkingSlot);

        return ReleaseSlotResponse.builder()
                .isSucc(true)
                .mesg("Slot id " + releaseSlotRequest.getSlotId() + " is released successfully from vehicle id " + releaseSlotRequest.getVehicleId() + " Thank You! " + releaseSlotRequest.getOwnerName())
                .build();
    }

    public BookSlotResponse bookSlot(BookSlotRequest bookSlotRequest) {
        ParkingSlot parkingSlot = parkingRepository.getSlotById(bookSlotRequest.getSlotId());
        if (parkingSlot.isOccupied()) {
            return BookSlotResponse.builder()
                    .isSuccess(false)
                    .msg("Slot id already occupied")
                    .build();
        }

        Vehicle vehicle = parkingRepository.getVehicleById(bookSlotRequest.getVehicleId());

        if (vehicle == null) {
            vehicle = Vehicle.builder()
                    .vehicleId(bookSlotRequest.getVehicleId())
                    .vehicleType(parkingSlot.getVehicleType())
                    .ownerName(bookSlotRequest.getOwnerName())
                    .build();
            parkingRepository.createVehicle(vehicle);
        }

        parkingSlot.setOccupied(true);
        parkingSlot.setVehicleId(bookSlotRequest.getVehicleId());


        parkingRepository.createAndUpdateSlot(parkingSlot);
        return BookSlotResponse.builder()
                .isSuccess(true)
                .msg("Slot id " + bookSlotRequest.getSlotId() + " is booked for vehicle " + bookSlotRequest.getVehicleId())
                .build();

    }

}

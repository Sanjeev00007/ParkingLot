package com.example.parking.data.requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReleaseSlotRequest {
    private String slotId;
    private String vehicleId;
    private String ownerName;
}

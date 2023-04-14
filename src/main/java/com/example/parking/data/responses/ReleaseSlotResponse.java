package com.example.parking.data.responses;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReleaseSlotResponse {
    private boolean isSucc;
    private String mesg;
}

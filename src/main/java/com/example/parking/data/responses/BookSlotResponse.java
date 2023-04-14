package com.example.parking.data.responses;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BookSlotResponse {
    private boolean isSuccess;
    private String msg;
}

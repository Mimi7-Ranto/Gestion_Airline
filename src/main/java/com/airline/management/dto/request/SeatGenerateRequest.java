package com.airline.management.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeatGenerateRequest {
    @NotNull
    @Min(1)
    private Integer rows;

    @NotNull
    @Min(1)
    private Integer seatsPerRow;

    // Optional default class for generated seats
    private String seatClass = "Economie";
}

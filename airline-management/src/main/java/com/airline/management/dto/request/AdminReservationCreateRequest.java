package com.airline.management.dto.request;

import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminReservationCreateRequest {
    @NotBlank
    private String volId;

    @NotNull
    @Min(1)
    private Integer nombrePassagers;

    private List<PassengerRequest> passagers;

    // Optional seat ids
    private List<String> seatIds;

    // Identify the client by email
    @NotBlank
    private String clientEmail;
}

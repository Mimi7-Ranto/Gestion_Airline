package com.airline.management.dto.request;

import com.airline.management.dto.request.PassengerRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class ReservationCreateRequest {
    @NotBlank
    private String volId;

    @NotNull
    @Min(1)
    private Integer nombrePassagers;

    private List<PassengerRequest> passagers;

    // Optional seat ids - handled later
    private List<String> seatIds;
}

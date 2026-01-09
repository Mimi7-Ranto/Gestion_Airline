package com.airline.management.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationResponse {
    private String id;
    private String volId;
    private Integer nombrePassagers;
    private BigDecimal montantTotal;
    private String statut;
    private LocalDateTime dateReservation;
}

package com.airline.management.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {
    private String id;
    private String numeroSiege;
    private Integer rangee;
    private String lettre;
    private String classe;
    private Boolean estFenetre;
    private Boolean estCouloir;
    private Boolean estSortieSecours;
}

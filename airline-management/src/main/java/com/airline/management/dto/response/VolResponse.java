package com.airline.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolResponse {
    private String idVol;
    private String idAeroportDepart;
    private String idAeroportDestination;
    private String idAvion;
    private LocalDateTime dateDepart;
    private LocalDateTime dateArrivee;
    private String idCompany;
    private Integer etatVol;
    private LocalDateTime arriveeEffective;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}

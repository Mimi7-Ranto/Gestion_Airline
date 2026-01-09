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
public class AeroportResponse {
    private String idAeroport;
    private String codeIata;
    private String codeIcao;
    private String nomAeroport;
    private String ville;
    private String pays;
    private String fuseauHoraire;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}

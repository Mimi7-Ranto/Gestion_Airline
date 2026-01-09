package com.airline.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolRequest {
    private String idAeroportDepart;
    private String idAeroportDestination;
    private String idAvion;
    @NotNull
    private LocalDateTime dateDepart;
    @NotNull
    private LocalDateTime dateArrivee;
    private String idCompany;
    private Integer etatVol;
}

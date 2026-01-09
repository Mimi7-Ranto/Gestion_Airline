package com.airline.management.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolRequest {
    @JsonProperty("aeroportDepartId")
    @NotBlank
    private String idAeroportDepart;
    @JsonProperty("aeroportDestinationId")
    @NotBlank
    private String idAeroportDestination;
    @NotBlank
    private String idAvion;
    @NotNull
    private LocalDateTime dateDepart;
    @NotNull
    private LocalDateTime dateArrivee;
    @NotBlank
    private String idCompany;
    private Integer etatVol;
    private String numeroVol;
    @NotNull
    private BigDecimal prixBase;
}

package com.airline.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AeroportRequest {
    @NotBlank
    @Size(min = 3, max = 3)
    private String codeIata;

    @Size(max = 4)
    private String codeIcao;

    @NotBlank
    private String nomAeroport;

    @NotBlank
    private String ville;

    @NotBlank
    private String pays;

    private String fuseauHoraire;
}

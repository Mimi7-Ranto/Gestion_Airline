package com.airline.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PassengerRequest {
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotNull
    private LocalDate dateNaissance;
    @NotBlank
    private String numeroPasseport;
    @NotBlank
    private String nationalite;
    private String email;
    private String telephone;
}

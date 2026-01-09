package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "etat_avion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtatAvion {
    @Id
    @Column(name = "etat_avion")
    private Integer etatAvion;

    @Column(name = "nom_etat", nullable = false)
    private String nomEtat;
}

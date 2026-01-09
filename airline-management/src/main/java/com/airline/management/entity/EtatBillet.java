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
@Table(name = "etat_billet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtatBillet {
    @Id
    @Column(name = "id_etat_billet")
    private Integer idEtatBillet;

    @Column(name = "nom_etat", nullable = false)
    private String nomEtat;
}

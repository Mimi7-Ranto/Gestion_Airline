package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "avion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avion {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "avion_seq"), @Parameter(name = "prefix", value = "AV"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_avion")
        private String idAvion;

    @Column(name = "modele", nullable = false)
    private String modele;

    @Column(name = "numero_serie", unique = true)
    private String numeroSerie;

    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Column(name = "annee_fabrication")
    private Integer anneeFabrication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etat_avion")
    private EtatAvion etatAvion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_company")
    private Company company;

    @Column(name = "date_derniere_maintenance")
    private LocalDate dateDerniereMaintenance;

    @Column(name = "heures_vol_totales")
    private Integer heuresVolTotales;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

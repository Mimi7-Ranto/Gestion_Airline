package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "vol")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vol {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "vol_seq"), @Parameter(name = "prefix", value = "VO"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_vol")
        private String idVol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aeroport_depart")
    private Aeroport aeroportDepart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aeroport_destination")
    private Aeroport aeroportDestination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_avion")
    private Avion avion;

    @Column(name = "date_depart", nullable = false)
    private LocalDateTime dateDepart;

    @Column(name = "date_arrivee", nullable = false)
    private LocalDateTime dateArrivee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_company")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etat_vol")
    private EtatVol etatVol;

    @Column(name = "arrivee_effective")
    private LocalDateTime arriveeEffective;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

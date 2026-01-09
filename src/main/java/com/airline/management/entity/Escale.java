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
@Table(name = "escale")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Escale {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "escale_seq"), @Parameter(name = "prefix", value = "ES"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_escale")
        private String idEscale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aeroport")
    private Aeroport aeroport;

    @Column(name = "ordre_escale")
    private Integer ordreEscale;

    @Column(name = "heure_arrivee")
    private LocalDateTime heureArrivee;

    @Column(name = "heure_depart")
    private LocalDateTime heureDepart;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}

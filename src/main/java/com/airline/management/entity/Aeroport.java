package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "aeroport")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aeroport {
        @Id
        @GeneratedValue(generator = "aeroport-trigger-id")
        @org.hibernate.annotations.GenericGenerator(name = "aeroport-trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value = "aeroport_seq"), @org.hibernate.annotations.Parameter(name = "prefix", value = "AP"), @org.hibernate.annotations.Parameter(name = "padding", value = "6")})
        @Column(name = "id_aeroport")
        private String idAeroport;

    @Column(name = "code_iata", nullable = false, length = 3)
    private String codeIata;

    @Column(name = "code_icao", length = 4)
    private String codeIcao;

    @Column(name = "nom_aeroport", nullable = false)
    private String nomAeroport;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "pays", nullable = false)
    private String pays;

    @Column(name = "fuseau_horaire")
    private String fuseauHoraire;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

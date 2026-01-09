package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "client_seq"), @Parameter(name = "prefix", value = "CL"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_client")
        private String idClient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_utilisateur", referencedColumnName = "id_utilisateur", unique = true)
    private Utilisateur utilisateur;

    @Column(name = "nom_client", nullable = false)
    private String nomClient;

    @Column(name = "prenom_client", nullable = false)
    private String prenomClient;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "date_naissance")
    private java.time.LocalDate dateNaissance;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

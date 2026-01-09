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
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {
    @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "company_seq"), @Parameter(name = "prefix", value = "CO"), @Parameter(name = "padding", value = "6")})
    @Column(name = "id_company")
    private String idCompany;

    @Column(name = "nom_company", nullable = false)
    private String nomCompany;

    @Column(name = "adresse_comp")
    private String adresseComp;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "email")
    private String email;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "code_iata", length = 3)
    private String codeIata;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

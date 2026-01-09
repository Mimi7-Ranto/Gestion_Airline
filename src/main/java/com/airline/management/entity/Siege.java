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
@Table(name = "siege")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Siege {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "siege_seq"), @Parameter(name = "prefix", value = "SI"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_siege")
        private String idSiege;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_avion")
    private Avion avion;

    @Column(name = "numero_siege", nullable = false)
    private String numeroSiege;

    @Column(name = "rangee", nullable = false)
    private Integer rangee;

    @Column(name = "lettre", nullable = false)
    private String lettre;

    @Column(name = "classe", nullable = false)
    private String classe;

    @Column(name = "est_fenetre")
    private Boolean estFenetre;

    @Column(name = "est_couloir")
    private Boolean estCouloir;

    @Column(name = "est_sortie_secours")
    private Boolean estSortieSecours;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}

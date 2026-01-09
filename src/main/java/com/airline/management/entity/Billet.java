package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "billet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Billet {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "billet_seq"), @Parameter(name = "prefix", value = "BI"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_billet")
        private String idBillet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_passager")
    private Passager passager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_siege")
    private Siege siege;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_etat_billet")
    private EtatBillet etatBillet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type_billet")
    private TypeBillet typeBillet;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    @Column(name = "numero_billet", unique = true)
    private String numeroBillet;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

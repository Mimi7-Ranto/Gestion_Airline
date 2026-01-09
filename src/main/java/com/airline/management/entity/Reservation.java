package com.airline.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "reservation", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_client", "id_vol"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "reservation_seq"), @Parameter(name = "prefix", value = "RS"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_reservation")
        private String idReservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vol")
    private Vol vol;

    @Column(name = "date_reservation")
    private LocalDateTime dateReservation;

    @Column(name = "nombre_passagers")
    private Integer nombrePassagers;

    @Column(name = "montant_total")
    private BigDecimal montantTotal;

    @Column(name = "statut")
    private String statut;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}

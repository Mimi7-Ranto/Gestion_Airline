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
@Table(name = "paiement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {
        @Id
        @GeneratedValue(generator = "trigger-id")
        @GenericGenerator(name = "trigger-id", strategy = "com.airline.management.config.PostgreSQLTriggerIdGenerator",
            parameters = {@Parameter(name = "sequence", value = "paiement_seq"), @Parameter(name = "prefix", value = "PY"), @Parameter(name = "padding", value = "6")})
        @Column(name = "id_paiement")
        private String idPaiement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reservation")
    private Reservation reservation;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "methode_paiement", nullable = false)
    private String methodePaiement;

    @Column(name = "statut")
    private String statut;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "date_paiement")
    private LocalDateTime datePaiement;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;
}

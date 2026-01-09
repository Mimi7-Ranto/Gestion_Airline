package com.airline.management.repository;

import com.airline.management.entity.Paiement;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, String> {
    List<Paiement> findByReservationIdReservation(String idReservation);
}

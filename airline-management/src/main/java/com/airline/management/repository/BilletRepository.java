package com.airline.management.repository;

import com.airline.management.entity.Billet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BilletRepository extends JpaRepository<Billet, String> {
    List<Billet> findByReservationIdReservation(String idReservation);
    List<Billet> findBySiegeIdSiege(String idSiege);
}

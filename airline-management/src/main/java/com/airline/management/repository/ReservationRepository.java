package com.airline.management.repository;

import com.airline.management.entity.Reservation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByClientIdClient(String idClient);
    List<Reservation> findByVolIdVol(String idVol);
}

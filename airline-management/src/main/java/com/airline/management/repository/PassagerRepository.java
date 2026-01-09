package com.airline.management.repository;

import com.airline.management.entity.Passager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassagerRepository extends JpaRepository<Passager, String> {
    List<Passager> findByReservationIdReservation(String idReservation);
}

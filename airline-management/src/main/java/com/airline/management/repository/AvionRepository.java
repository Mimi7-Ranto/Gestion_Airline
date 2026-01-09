package com.airline.management.repository;

import com.airline.management.entity.Avion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvionRepository extends JpaRepository<Avion, String> {
    Optional<Avion> findByNumeroSerie(String numeroSerie);
}

package com.airline.management.repository;

import com.airline.management.entity.Aeroport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AeroportRepository extends JpaRepository<Aeroport, String> {
    Optional<Aeroport> findByCodeIata(String codeIata);
}

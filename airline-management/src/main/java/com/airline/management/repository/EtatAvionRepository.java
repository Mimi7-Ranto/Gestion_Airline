package com.airline.management.repository;

import com.airline.management.entity.EtatAvion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatAvionRepository extends JpaRepository<EtatAvion, Integer> {
}

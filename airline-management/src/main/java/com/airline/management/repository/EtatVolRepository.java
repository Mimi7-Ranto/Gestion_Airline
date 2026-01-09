package com.airline.management.repository;

import com.airline.management.entity.EtatVol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatVolRepository extends JpaRepository<EtatVol, Integer> {
}

package com.airline.management.repository;

import com.airline.management.entity.EtatBillet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatBilletRepository extends JpaRepository<EtatBillet, Integer> {
}

package com.airline.management.repository;

import com.airline.management.entity.Escale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscaleRepository extends JpaRepository<Escale, String> {
    List<Escale> findByVolIdVol(String idVol);
}

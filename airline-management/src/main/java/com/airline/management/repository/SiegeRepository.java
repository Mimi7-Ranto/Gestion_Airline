package com.airline.management.repository;

import com.airline.management.entity.Siege;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiegeRepository extends JpaRepository<Siege, String> {
    List<Siege> findByAvionIdAvion(String idAvion);
}

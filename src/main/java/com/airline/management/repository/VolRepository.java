package com.airline.management.repository;

import com.airline.management.entity.Vol;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolRepository extends JpaRepository<Vol, String> {
    List<Vol> findByAeroportDepartIdAeroportAndDateDepartBetween(String idAeroportDepart, LocalDateTime from, LocalDateTime to);
}

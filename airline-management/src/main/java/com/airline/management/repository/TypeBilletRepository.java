package com.airline.management.repository;

import com.airline.management.entity.TypeBillet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeBilletRepository extends JpaRepository<TypeBillet, Integer> {
}

package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.serie.LogSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogSerieRepository extends JpaRepository<LogSerie, Integer> {
}

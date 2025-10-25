package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.produccion.PegatinaDecorativa;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ProPegatinaDecorativaRepository extends JpaRepository<PegatinaDecorativa, Integer>{
}

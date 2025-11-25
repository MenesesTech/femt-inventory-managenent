package com.femt.inventory_management.repository;

import com.femt.inventory_management.models.kit.KitSerieColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KitSerieManagerRepository extends JpaRepository<KitSerieColor, Integer> {

}

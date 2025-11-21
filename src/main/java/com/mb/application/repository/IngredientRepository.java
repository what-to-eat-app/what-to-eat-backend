package com.mb.application.repository;

import java.util.List;

import com.mb.application.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

    List<IngredientEntity> findByNameIgnoreCase(String name);

}

package com.mb.application.repository;

import java.util.List;

import com.mb.application.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientDao extends JpaRepository<IngredientEntity, Long> {

    List<IngredientEntity> findByRecipeId(Integer id);

    List<IngredientEntity> findByNameIgnoreCase(String name);

}

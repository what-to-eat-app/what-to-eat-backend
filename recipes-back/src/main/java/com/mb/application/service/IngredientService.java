package com.mb.application.service;

import com.mb.application.controller.request.AddIngredientRequest;
import com.mb.application.controller.request.UpdateIngredientRequest;
import com.mb.application.controller.response.IngredientResponse;
import com.mb.application.entity.IngredientEntity;
import com.mb.application.exception.ResourceNotFoundException;
import com.mb.application.repository.IngredientDao;
import com.mb.application.util.Util;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientDao ingredientDao;

    @Transactional(readOnly = true)
    public List<IngredientResponse> listIngredients(String name) {

        List<IngredientResponse> ingredients;
        if (name != null) {
            ingredients = ingredientDao.findByNameIgnoreCase(name).stream()
                    .map(this::buildIngredientModel)
                    .filter(Util.distinctByKey(IngredientResponse::recipeId))
                    .toList();
        } else {
            ingredients = new ArrayList<>(ingredientDao.findAll().stream()
                    .map(this::buildIngredientModel)
                    .filter(Util.distinctByKey(IngredientResponse::recipeId))
                    .toList());
        }

//        ingredients
//                .removeAll(ingredients.stream()
//                        .filter(ingredient -> !StringUtils.isAlpha(ingredient.name()))
//                        .toList());
//
//        ingredients.forEach(ingredient -> {
//            ingredient.setName(ingredient.name().substring(0, 1).toUpperCase() + i.getName().substring(1));
//
//        });

        return ingredients;
    }

    @Transactional(readOnly = true)
    public IngredientResponse getIngredient(Long ingredientId) {
        var ingredientEntity = findIngredientById(ingredientId);
        return buildIngredientModel(ingredientEntity);
    }

    @Transactional
    public IngredientResponse createIngredient(AddIngredientRequest createdIngredient) {
        var ingredientEntity = IngredientEntity.builder()
                .name(createdIngredient.name())
                .measure(createdIngredient.measure())
                .unit(createdIngredient.unit())
                .recipeId(createdIngredient.recipeId())
                .build();

        return buildIngredientModel(ingredientDao.save(ingredientEntity));
    }

    @Transactional
    public IngredientResponse updateIngredient(Long ingredientId, UpdateIngredientRequest updatedIngredient) {
        var ingredientEntity = findIngredientById(ingredientId);

        ingredientEntity.setName(updatedIngredient.name());
        ingredientEntity.setMeasure(updatedIngredient.measure());
        ingredientEntity.setUnit(updatedIngredient.unit());
        ingredientEntity.setRecipeId(updatedIngredient.recipeId());

        return buildIngredientModel(ingredientDao.save(ingredientEntity));

    }


    public void deleteIngredient(Long ingredientId) {
        findIngredientById(ingredientId);
        ingredientDao.deleteById(ingredientId);
    }

    private IngredientEntity findIngredientById(Long ingredientId) {
        return ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Ingredient #%d  not found", ingredientId)));
    }

    public IngredientResponse buildIngredientModel(IngredientEntity ingredientEntity) {
        if (ingredientEntity == null) {
            return null;
        }
        return IngredientResponse.builder()
                .id(ingredientEntity.getId())
                .name(ingredientEntity.getName())
                .measure(ingredientEntity.getMeasure())
                .unit(ingredientEntity.getUnit())
                .recipeId(ingredientEntity.getRecipeId())
                .build();

    }


}

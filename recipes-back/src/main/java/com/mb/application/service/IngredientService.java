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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private static final Logger LOG = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientDao ingredientDao;

    public List<IngredientResponse> listIngredients(String name) {

        List<IngredientResponse> ingredients = new ArrayList<>();
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

    public IngredientResponse getIngredient(Long ingredientId) {
        var ingredientEntity = ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Ingredient #%d  not found", ingredientId)));
        return buildIngredientModel(ingredientEntity);
    }

    public IngredientResponse createIngredient(AddIngredientRequest ingredient) {
        var ingredientEntity = new IngredientEntity();
        ingredientEntity.setName(ingredient.name());
        ingredientEntity.setMeasure(ingredient.measure());

        return buildIngredientModel(ingredientDao.save(ingredientEntity));
    }

    public IngredientResponse updateIngredient(Long id, UpdateIngredientRequest ingredient) {
        var ingredientEntity = new IngredientEntity();

        ingredientEntity.setId(id);
        ingredientEntity.setName(ingredient.name());
        ingredientEntity.setMeasure(ingredient.measure());
        ingredientEntity.setRecipeId(ingredient.recipeId());

        return buildIngredientModel(ingredientDao.save(ingredientEntity));

    }

    public void deleteRecipe(Long ingredientId) {
        ingredientDao.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Ingredient #%d  not found", ingredientId)));
        ingredientDao.deleteById(ingredientId);
    }

    public IngredientResponse buildIngredientModel(IngredientEntity ingredientEntity) {
        if (ingredientEntity == null) {
            return null;
        }
        return IngredientResponse.builder()
                .id(ingredientEntity.getId())
                .name(ingredientEntity.getName())
                .measure(ingredientEntity.getMeasure())
                .recipeId(ingredientEntity.getRecipeId())
                .build();

    }


}

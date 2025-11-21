package com.mb.application.controller.mapper;

import com.mb.application.controller.response.IngredientResponse;
import com.mb.application.entity.IngredientEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class IngredientMapper {

    public static IngredientEntity mapIngredientEntity(
            String name,
            String measure,
            String unit
    ) {
        return IngredientEntity.builder()
                .name(name)
                .measure(measure)
                .unit(unit)
                .build();
    }


    public static IngredientResponse mapIngredientResponse(IngredientEntity ingredient, boolean shallow) {
        var ingredientResponse = IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .measure(ingredient.getMeasure())
                .unit(ingredient.getUnit());
        if (!shallow) {
            ingredientResponse.recipes(ingredient.getRecipes() != null ?
                    ingredient.getRecipes().stream().map(r -> RecipeMapper.mapToRecipeResponse(r, true)).collect(Collectors.toSet())
                    : Set.of());
        }
        return ingredientResponse.build();
    }
}

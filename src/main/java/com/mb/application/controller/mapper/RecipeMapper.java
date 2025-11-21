package com.mb.application.controller.mapper;

import com.mb.application.controller.request.UpdateRecipeRequest;
import com.mb.application.controller.response.RecipeResponse;
import com.mb.application.entity.RecipeEntity;

import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeResponse mapToRecipeResponse(RecipeEntity recipe, boolean shallow) {
        var recipeResponse = RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .category(recipe.getCategory())
                .url(recipe.getUrl())
                .description(recipe.getDescription());
        if(!shallow) {
            recipeResponse.ingredients(recipe.getIngredients().stream()
                    .map(i -> IngredientMapper.mapIngredientResponse(i, true))
                    .collect(Collectors.toSet()));
        }
        return recipeResponse.build();
    }

    public static RecipeEntity mapToRecipeEntity(UpdateRecipeRequest updatedRecipe) {
        return RecipeEntity.builder()
                .name(updatedRecipe.name())
                .category(updatedRecipe.category())
                .url(updatedRecipe.url())
                .description(updatedRecipe.description())
                .build();
    }
}

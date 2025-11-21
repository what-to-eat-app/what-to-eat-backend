package com.mb.application.controller.response;

import lombok.Builder;

import java.util.Set;

@Builder
public record RecipeResponse(Long id,
                             String name,
                             String url,
                             String category,
                             String description,
                             Set<IngredientResponse> ingredients) {
}

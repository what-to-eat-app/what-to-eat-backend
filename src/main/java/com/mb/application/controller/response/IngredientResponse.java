package com.mb.application.controller.response;

import lombok.Builder;
import lombok.Setter;

import java.util.Set;

@Builder
public record IngredientResponse(Long id,
                                 String name,
                                 String measure,
                                 String unit,
                                 Set<RecipeResponse> recipes) {
}

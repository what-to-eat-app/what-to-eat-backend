package com.mb.application.controller.response;

import lombok.Builder;
import lombok.Setter;

@Builder
public record IngredientResponse(Long id,
                                 String name,
                                 String measure,
                                 String unit,
                                 Long recipeId) {
}

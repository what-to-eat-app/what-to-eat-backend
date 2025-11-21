package com.mb.application.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddIngredientRequest(@NotBlank
                                   String name,
                                   String measure,
                                   String unit,
                                   Long recipeId) {
}

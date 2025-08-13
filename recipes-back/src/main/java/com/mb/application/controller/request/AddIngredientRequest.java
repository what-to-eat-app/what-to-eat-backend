package com.mb.application.controller.request;

import lombok.Builder;

@Builder
public record AddIngredientRequest(String name,
                                String measure,
                                String unit,
                                Long recipeId) {
}

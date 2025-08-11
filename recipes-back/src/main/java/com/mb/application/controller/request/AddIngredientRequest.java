package com.mb.application.controller.request;

import lombok.Builder;

@Builder
public record AddIngredientRequest(String name,
                                String measure,
                                Long recipeId) {
}

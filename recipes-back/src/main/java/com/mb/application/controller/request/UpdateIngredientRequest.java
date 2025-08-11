package com.mb.application.controller.request;

import lombok.Builder;

@Builder
public record UpdateIngredientRequest(String name,
                                      String measure,
                                      Long recipeId) {
}

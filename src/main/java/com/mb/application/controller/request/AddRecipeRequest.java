package com.mb.application.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.Set;

@Builder
public record AddRecipeRequest(@NotBlank
                               String name,
                               String url,
                               String category,
                               String description,
                               Set<Long> ingredientIds) {
}

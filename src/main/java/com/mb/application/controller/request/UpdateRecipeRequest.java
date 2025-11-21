package com.mb.application.controller.request;

import java.util.Set;

public record UpdateRecipeRequest(String name,
                                  String url,
                                  String category,
                                  String description,
                                  Set<Long> ingredientIds) {
}

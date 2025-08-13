package com.mb.application.controller;

import com.mb.application.controller.request.AddIngredientRequest;
import com.mb.application.controller.request.UpdateIngredientRequest;
import com.mb.application.controller.response.IngredientResponse;
import com.mb.application.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientApiController {

    @Autowired
    IngredientService ingredientService;

    @GetMapping
    public List<IngredientResponse> listIngredients(String fields, Integer offset, Integer limit, String name, Integer recipeId) {
        return ingredientService.listIngredients(name);
    }

    @GetMapping("/{ingredient_id}")
    public IngredientResponse retrieveIngredient(@PathVariable("ingredient_id") Long ingredientId) {
        return ingredientService.getIngredient(ingredientId);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse createIngredient(@RequestBody AddIngredientRequest ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @DeleteMapping("/{ingredient_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable("ingredient_id") Long id) {
        ingredientService.deleteIngredient(id);
    }

    @PutMapping("/{ingredient_id}")
    public IngredientResponse updateIngredient(@PathVariable("ingredient_id") Long id, @RequestBody UpdateIngredientRequest ingredientToUpdate) {
        return ingredientService.updateIngredient(id, ingredientToUpdate);
    }
}

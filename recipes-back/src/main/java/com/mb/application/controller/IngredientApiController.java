package com.mb.application.controller;

import com.mb.application.controller.request.AddIngredientRequest;
import com.mb.application.controller.request.UpdateIngredientRequest;
import com.mb.application.controller.response.IngredientResponse;
import com.mb.application.exception.ResourceNotFoundException;
import com.mb.application.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("/api/v1/ingredients")
public class IngredientApiController {

    @Autowired
    IngredientService is;

    @GetMapping
    public List<IngredientResponse> listIngredients(String fields, Integer offset, Integer limit, String name, Integer recipeId) {
        return is.listIngredients(name);
    }

    @GetMapping("/{ingredient_id}")
    public IngredientResponse retrieveIngredient(@PathVariable("ingredient_id") Long ingredientId) {
        var ingredient = is.getIngredient(ingredientId);
        if (ingredient == null) {
            throw new ResourceNotFoundException(String.format("Recipe not found for recipe id: %d", ingredientId));
        }
        return ingredient;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse createIngredient(@RequestBody AddIngredientRequest ingredient) {
        return is.createIngredient(ingredient);
    }

    @DeleteMapping("/{ingredient_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteIngredient(Long id) {
        is.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/{ingredient_id}")
    public IngredientResponse updateIngredient(@PathVariable("ingredient_id") Long id, @RequestBody UpdateIngredientRequest ingredientToUpdate) {
        return is.updateIngredient(id, ingredientToUpdate);
    }
}

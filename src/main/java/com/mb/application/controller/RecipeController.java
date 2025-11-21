package com.mb.application.controller;

import com.mb.application.controller.request.AddRecipeRequest;
import com.mb.application.controller.request.UpdateRecipeRequest;
import com.mb.application.controller.response.RecipeResponse;
import com.mb.application.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RecipeResponse createRecipe(@RequestBody AddRecipeRequest recipe) {
        return recipeService.createRecipe(recipe);

    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") String id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public Set<RecipeResponse> listRecipes(@RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size, @RequestParam(value = "name", required = false) String name) {
        return recipeService.listRecipes(page, size);
    }

    @GetMapping("/{id}")
    public RecipeResponse retrieveRecipe(@PathVariable("id") String id) {
        return recipeService.getRecipe(id);
    }

    @PutMapping("/{id}")
    public RecipeResponse updateRecipe(@PathVariable("id") String id, @RequestBody UpdateRecipeRequest recipe) {
        return recipeService.updateRecipe(id, recipe);
    }
}
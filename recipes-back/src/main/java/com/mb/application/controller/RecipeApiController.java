package com.mb.application.controller;

import java.util.List;

import com.mb.application.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mb.application.service.RecipeService;
import com.mb.server.api.RecipesApi;
import com.mb.server.model.Recipe;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("${openapi.recipeManagement.base-path:/api/v1}")
//@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class RecipeApiController implements RecipesApi {

    @Autowired
    RecipeService rs;

    //@PreAuthorize("hasAuthority('WRITE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe created = rs.createRecipe(recipe);
        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    //@PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("id") String id) {
        rs.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //@PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Recipe>> listRecipes(@RequestParam("page") Integer page,
            @RequestParam("size") Integer size, @RequestParam(value = "name", required = false) String name) {
        List<Recipe> recipes = rs.listRecipes(page, size);
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Recipe> retrieveRecipe(@PathVariable("id") String id) {
        Recipe recipe = rs.getRecipe(id);
        if (recipe == null) {
            throw new ResourceNotFoundException("Recipe not found for recipe id: " + id);
        }
        return new ResponseEntity<>(recipe, HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") String id, @RequestBody Recipe recipe) {
        int updatedId = rs.updateRecipe(id, recipe);
        ResponseEntity<Recipe>  updatedRecipe = retrieveRecipe(Integer.toString(updatedId));
        return updatedRecipe;
    }

}

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

import com.mb.application.service.IngredientService;
import com.mb.server.api.IngredientsApi;
import com.mb.server.model.Ingredient;

@Controller
@RequestMapping("${openapi.recipeManagement.base-path:/api/v1}")
//@PreAuthorize("hasAnyRole('ADMIN','USER')")
public class IngredientApiController implements IngredientsApi {

    @Autowired
    IngredientService is;

    //@PreAuthorize("hasAuthority('WRITE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        Ingredient created = is.createIngredient(ingredient);
        return new ResponseEntity<>(created, HttpStatus.CREATED);

    }

    //@PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Void> deleteIngredient(String id) {
        is.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //@PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<Ingredient>> listIngredients( String fields, Integer offset,  Integer limit,  String name, Integer recipeId) {
        List<Ingredient> ingredients = is.listIngredients(name);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('READ_PRIVILEGE') and hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Ingredient> retrieveIngredient(@PathVariable("id") String id) {
        Ingredient ingredient = is.getIngredient(id);
        if (ingredient == null) {
            throw new ResourceNotFoundException("Recipe not found for recipe id: " + id);
        }
        return new ResponseEntity<>(ingredient, HttpStatus.OK);

    }

    //@PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasRole('ADMIN')")
    public ResponseEntity<Ingredient> updateIngredient(String id, @RequestBody Ingredient ingredient) {
        int updatedId = is.updateIngredient(id, ingredient);
        return retrieveIngredient(Integer.toString(updatedId));
    }
}

/*
package com.mb.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mb.application.entity.IngredientEntity;
import com.mb.application.entity.InstructionEntity;
import com.mb.application.entity.RecipeEntity;
import com.mb.application.repository.IngredientDao;
import com.mb.application.repository.InstructionDao;
import com.mb.application.repository.RecipeDao;
import com.mb.application.util.Util;
import org.aspectj.apache.bcel.generic.Instruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private static final Logger LOG = LoggerFactory.getLogger(RecipeService.class);
    private final static String ID = "id";
    private final static String ASC = "ASC";

    @Autowired
    RecipeDao recipeDao;

    @Autowired
    IngredientDao ingredientDao;

    @Autowired
    InstructionDao instructionDao;

    public long getRecipesCount() {
        return recipeDao.count();
    }

    public List<Recipe> listRecipes(int pageNo, int pageSize) {
        Sort sort = Sort.by(ID).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<RecipeEntity> recipes = recipeDao.findAll(pageable);
        return recipes.stream().map(this::buildRecipeModel).collect(Collectors.toList());
    }

    public Recipe getRecipe(String id) {
        Optional<RecipeEntity> recipeEntity = recipeDao.findById(Integer.valueOf(id));
        return recipeEntity.map(this::buildRecipeModel).orElse(null);
    }

    public Recipe createRecipe(Recipe recipe) {

        RecipeEntity recipeEntity = new RecipeEntity();
        recipeEntity.setId(recipe.getId());
        recipeEntity.setName(recipe.getName());
        recipeEntity.setUrl(recipe.getUrl());
        recipeEntity.setCategory(recipe.getCategory());

        RecipeEntity savedRecipe = recipeDao.save(recipeEntity);

        return buildRecipeModel(savedRecipe);

    }

    public int updateRecipe(String id, Recipe recipe) {
        ObjectMapper objectMapper = new ObjectMapper();
        RecipeEntity recipeEntity = new RecipeEntity();
        String recipeString;
        try {
            recipeString = objectMapper.writeValueAsString(recipe);
            recipeEntity = objectMapper.readValue(recipeString, RecipeEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Error during conversion to String : {}", e));
        }

        return recipeDao.save(recipeEntity).getId();

    }

    public void deleteRecipe(String id) {
        Recipe recipe = getRecipe(id);
        if (recipe == null) {
            return;
        }
        recipeDao.deleteById(Integer.valueOf(id));
    }

    private Recipe buildRecipeModel(RecipeEntity recipeEntity) {
        Recipe recipe = new Recipe();

        recipe.setId(recipeEntity.getId());
        recipe.setName(recipeEntity.getName());
        recipe.setCategory(recipeEntity.getCategory());
        recipe.setUrl(recipeEntity.getUrl());
        recipe.setImage(recipeEntity.getImage());
        recipe.setDescription(recipeEntity.getDescription());
        List<Ingredient> ingredients = ingredientDao.findByRecipeId(recipe.getId()).stream()
                .map(this::buildIngredientModel).filter(Util.distinctByKey(Ingredient::getName))
                .collect(Collectors.toList());
        recipe.setIngredients(ingredients);
        List<Instruction> instructions = instructionDao
                .findByRecipeId(recipe.getId()).stream().map(this::buildInstructionModel).filter(Util
                        .distinctByKey(Instruction::getDescription).and(Util.distinctByKey(Instruction::getPosition)))
                .collect(Collectors.toList());
        recipe.setInstructions(instructions);

        return recipe;
    }

    private Ingredient buildIngredientModel(IngredientEntity ingredientEntity) {
        Ingredient ingredient = new Ingredient();

        //ingredient.setId(ingredientEntity.getId());
        ingredient.setName(ingredientEntity.getName());
        //ingredient.setSubtitle(ingredientEntity.getSubtitle());
        ingredient.setMeasure(ingredientEntity.getMeasure());

        return ingredient;
    }

    private Instruction buildInstructionModel(InstructionEntity instructionEntity) {
        Instruction instruction = new Instruction();

        instruction.setId(instructionEntity.getId());
        instruction.setDescription(instructionEntity.getDescription());
        instruction.setPosition(String.valueOf(Integer.parseInt(instructionEntity.getPos()) + 1));

        return instruction;
    }

}
*/

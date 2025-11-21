package com.mb.application.service;

import com.mb.application.controller.mapper.RecipeMapper;
import com.mb.application.controller.request.AddRecipeRequest;
import com.mb.application.controller.request.UpdateRecipeRequest;
import com.mb.application.controller.response.RecipeResponse;
import com.mb.application.entity.RecipeEntity;
import com.mb.application.exception.RecipeAlreadyExistsException;
import com.mb.application.exception.ResourceNotFoundException;
import com.mb.application.repository.IngredientRepository;
import com.mb.application.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final static String ID = "id";

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;


    public Set<RecipeResponse> listRecipes(int pageNo, int pageSize) {
        Sort sort = Sort.by(ID).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<RecipeEntity> recipes = recipeRepository.findAll(pageable);
        return recipes.stream().map(r -> RecipeMapper.mapToRecipeResponse(r, false)).collect(Collectors.toSet());
    }

    public RecipeResponse getRecipe(String id) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(Long.valueOf(id));
        if (recipeEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Recipe #%s not found", id));
        }
        return RecipeMapper.mapToRecipeResponse(recipeEntity.get(), false);
    }

    @Transactional
    public RecipeResponse createRecipe(AddRecipeRequest recipeToCreate) {

        var existingRecipe = recipeRepository.findByName(recipeToCreate.name());
        if (existingRecipe.isPresent()) {
            throw new RecipeAlreadyExistsException("A recipe with the same name exists");
        }

        var recipe = RecipeEntity.builder()
                .name(recipeToCreate.name())
                .description(recipeToCreate.description())
                .category(recipeToCreate.category())
                .url(recipeToCreate.url())
                .build();

        if (!recipeToCreate.ingredientIds().isEmpty()) {
            var ingredients = ingredientRepository.findAllById(recipeToCreate.ingredientIds());
            recipe.setIngredients(ingredients);
        }
        var savedRecipe = recipeRepository.save(recipe);

        return RecipeMapper.mapToRecipeResponse(savedRecipe, false);

    }

    @Transactional
    public RecipeResponse updateRecipe(String id, UpdateRecipeRequest recipeToUpdate) {
        var recipe = recipeRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Recipe #%s not found", id)));
        recipe.setName(StringUtils.isBlank(recipeToUpdate.name()) ? recipe.getName() : recipeToUpdate.name());
        recipe.setDescription(StringUtils.isBlank(recipeToUpdate.description()) ? recipe.getDescription() : recipeToUpdate.description());
        recipe.setCategory(StringUtils.isBlank(recipeToUpdate.category()) ? recipe.getCategory() : recipeToUpdate.category());
        recipe.setUrl(StringUtils.isBlank(recipeToUpdate.url()) ? recipe.getUrl() : recipeToUpdate.url());
        if (recipeToUpdate.ingredientIds() != null && !recipeToUpdate.ingredientIds().isEmpty()) {
            var ingredients = ingredientRepository.findAllById(recipeToUpdate.ingredientIds());
            recipe.setIngredients(ingredients);
        }

        return RecipeMapper.mapToRecipeResponse(recipe, false);

    }

    @Transactional
    public void deleteRecipe(String id) {
        Optional<RecipeEntity> recipeEntity = recipeRepository.findById(Long.valueOf(id));
        if (recipeEntity.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Recipe #%s not found", id));
        }
        recipeRepository.deleteById(Long.valueOf(id));
    }
}

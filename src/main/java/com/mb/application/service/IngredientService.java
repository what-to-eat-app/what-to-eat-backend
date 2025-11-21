package com.mb.application.service;

import com.mb.application.controller.mapper.IngredientMapper;
import com.mb.application.controller.request.AddIngredientRequest;
import com.mb.application.controller.request.UpdateIngredientRequest;
import com.mb.application.controller.response.IngredientResponse;
import com.mb.application.entity.IngredientEntity;
import com.mb.application.exception.ResourceNotFoundException;
import com.mb.application.repository.IngredientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    @Transactional(readOnly = true)
    public Set<IngredientResponse> listIngredients(String name) {

        Set<IngredientResponse> ingredients;
        if (name != null) {
            ingredients = ingredientRepository.findByNameIgnoreCase(name).stream()
                    .map(i -> IngredientMapper.mapIngredientResponse(i, false)).collect(Collectors.toSet());
        } else {
            ingredients = ingredientRepository.findAll().stream()
                    .map(i -> IngredientMapper.mapIngredientResponse(i, false)).collect(Collectors.toSet());
        }
        return ingredients;
    }

    @Transactional(readOnly = true)
    public IngredientResponse getIngredient(Long ingredientId) {
        var ingredientEntity = findIngredientById(ingredientId);
        return IngredientMapper.mapIngredientResponse(ingredientEntity, false);
    }

    @Transactional
    public IngredientResponse createIngredient(AddIngredientRequest createdIngredient) {
        var ingredientEntity = IngredientMapper.mapIngredientEntity(
                createdIngredient.name(),
                createdIngredient.measure(),
                createdIngredient.unit()
        );

        return IngredientMapper.mapIngredientResponse(ingredientRepository.save(ingredientEntity), false);
    }


    @Transactional
    public IngredientResponse updateIngredient(Long ingredientId, UpdateIngredientRequest updatedIngredient) {
        var ingredientEntity = findIngredientById(ingredientId);

        IngredientMapper.mapIngredientEntity(
                updatedIngredient.name(),
                updatedIngredient.measure(),
                updatedIngredient.unit()
        );

        return IngredientMapper.mapIngredientResponse(ingredientRepository.save(ingredientEntity), false);

    }


    public void deleteIngredient(Long ingredientId) {
        findIngredientById(ingredientId);
        ingredientRepository.deleteById(ingredientId);
    }

    private IngredientEntity findIngredientById(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Ingredient #%d  not found", ingredientId)));
    }




}

package com.mb.recipesback;

import com.mb.application.controller.request.AddIngredientRequest;
import com.mb.application.repository.IngredientDao;
import com.mb.application.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class IngredientServiceTest {

    private static final String INGREDIENT_NAME = "Ingredient-1";
    private static final String MEASURE = "1/2 cup";

    private IngredientService ingredientService;

    IngredientDao ingredientDao;

    @BeforeEach
    public void setUp(){
        ingredientDao = mock(IngredientDao.class);
    }


    @Nested
    class CreateIngredientTest {

        @Test
        public void givenIngredientList_ingredientCreated() {
            //GIVEN
            var request = AddIngredientRequest.builder()
                    .name(INGREDIENT_NAME)
                    .measure(MEASURE)
                    .recipeId(1L)
                    .build();
            ingredientService = new IngredientService(ingredientDao);
            //WHEN
           ingredientService.createIngredient(request);
            //THEN
            verify(ingredientDao, times(1)).save(any());
        }
    }
}

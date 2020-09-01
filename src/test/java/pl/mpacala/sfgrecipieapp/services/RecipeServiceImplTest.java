package pl.mpacala.sfgrecipieapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.mpacala.sfgrecipieapp.converters.RecipeCommandToRecipe;
import pl.mpacala.sfgrecipieapp.converters.RecipeToRecipeCommand;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    //cmd+N -> setUp method
    @BeforeEach
    void setUp() {
        //initialize mocks from this class
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {

        Recipe recipe1 = new Recipe();
        Set<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe1);

        //recipeService.getRecipes() will ask recipeRepo for recipe set, then recipesData will be returned
        //static import org.mockito.Mockito.when
        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        //assertEquals(recipes.size(), 0); //passes, so mockito gets us empty set
        assertEquals(recipes.size(), 1); //will return 1 because of Mockito when.thenReturn

        //call findAll() on recipeRepo one time to verify
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void findRecipeById() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findRecipeById(1L);
        assertNotNull(recipeReturned, "Null recipe");
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    void testDeleteById() throws Exception {
        Long idToDelete = 1L;

        recipeService.deleteById(idToDelete);

        //when not needed, it returns void
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}
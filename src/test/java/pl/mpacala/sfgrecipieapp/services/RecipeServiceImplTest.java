package pl.mpacala.sfgrecipieapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    //cmd+N -> setUp method
    @BeforeEach
    void setUp() {
        //initialize mocks from this class
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
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
}
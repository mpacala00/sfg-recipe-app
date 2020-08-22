package pl.mpacala.sfgrecipieapp.services;

import pl.mpacala.sfgrecipieapp.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
    Recipe findRecipeById(Long id);
}

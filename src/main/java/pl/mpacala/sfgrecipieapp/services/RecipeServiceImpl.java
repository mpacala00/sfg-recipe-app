package pl.mpacala.sfgrecipieapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mpacala.sfgrecipieapp.commands.RecipeCommand;
import pl.mpacala.sfgrecipieapp.converters.RecipeCommandToRecipe;
import pl.mpacala.sfgrecipieapp.converters.RecipeToRecipeCommand;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j //gives us a logger
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipeSet = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipe -> { recipeSet.add(recipe); });
        return recipeSet;
    }

    @Override
    public Recipe findRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id).get();
        if(recipe == null)
            throw new RuntimeException("No recipe found under this id!");
        return recipe;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        //calling other method of this service as an argument
        return recipeToRecipeCommand.convert( findRecipeById(id) );
    }

    //method that allows to quickly save RecipeCommand objects as Recipe in RecipeRepo
    //by converting to Recipe and back to RecipeCommand after save
    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        //just a pojo not an Entity - detached from Hibernate context
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved recipe with id: "+savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }


}

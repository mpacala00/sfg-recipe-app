package pl.mpacala.sfgrecipieapp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mpacala.sfgrecipieapp.commands.IngredientCommand;
import pl.mpacala.sfgrecipieapp.converters.IngredientToIngredientCommand;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand command;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand command) {
        this.recipeRepository = recipeRepository;
        this.command = command;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()) {
            log.debug("Error. Could not find recipe of id: "+recipeId);
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .map(command::convert).findFirst();

            if(!ingredientCommandOptional.isPresent()) {
                log.debug("Error. Could not find ingredient of id: "+ingredientId+" in the recipe of id: "+recipeId);
            }

            return ingredientCommandOptional.get();
        }

        log.debug("Returned value is null");
        return null;
    }
}

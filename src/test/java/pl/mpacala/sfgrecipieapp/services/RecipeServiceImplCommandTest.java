package pl.mpacala.sfgrecipieapp.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.mpacala.sfgrecipieapp.commands.RecipeCommand;
import pl.mpacala.sfgrecipieapp.converters.RecipeCommandToRecipe;
import pl.mpacala.sfgrecipieapp.converters.RecipeToRecipeCommand;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//SpringBootTest allows to take the whole context in this test so we can inject these 4 recipe related things
@SpringBootTest
public class RecipeServiceImplCommandTest {

    public final static String NEW_DESC = "description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    @Transactional
    public void testSaveOfDescription() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESC);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertEquals(NEW_DESC, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}

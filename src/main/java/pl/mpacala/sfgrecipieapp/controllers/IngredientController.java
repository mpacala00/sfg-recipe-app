package pl.mpacala.sfgrecipieapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mpacala.sfgrecipieapp.services.IngredientService;
import pl.mpacala.sfgrecipieapp.services.RecipeService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String getIngredients(@PathVariable String recipeId, Model model) {
        log.debug("Getting ingredients for recipe id: "+recipeId);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
        return "/recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String getIngredients(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        log.debug("Showing ingredient of id: "+ ingredientId +" for recipe id: "+recipeId);

        model.addAttribute("ingredient", ingredientService.
                findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "/recipe/ingredient/show";
    }
}

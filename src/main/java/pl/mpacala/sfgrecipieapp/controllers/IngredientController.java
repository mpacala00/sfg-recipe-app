package pl.mpacala.sfgrecipieapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import pl.mpacala.sfgrecipieapp.commands.IngredientCommand;
import pl.mpacala.sfgrecipieapp.commands.RecipeCommand;
import pl.mpacala.sfgrecipieapp.commands.UnitOfMeasureCommand;
import pl.mpacala.sfgrecipieapp.services.IngredientService;
import pl.mpacala.sfgrecipieapp.services.RecipeService;
import pl.mpacala.sfgrecipieapp.services.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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

        //for unit of measure list required for updating ingredient
        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "/recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId,
                                   Model model) {

        model.addAttribute("ingredient", ingredientService
                .findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.findAll());

        return "/recipe/ingredient/ingredientForm";
    }

    //it is supposed to be PostMapping but it does not work with post mapping
    //it does with GetMapping
    //im so confused
    @GetMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("Saved for recipe id: "+command.getRecipeId());
        log.debug("Saved ingredient id: "+command.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model) throws NullPointerException {

        //make sure we have a proper id value
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        if(recipeCommand == null) {
            throw new NullPointerException();
        }

        //creating a new ingredient with the id of the recipe assigned to it
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId,
                                   Model model) {
        log.debug("Attempting to delete ingredient of id: "+ingredientId);
        ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

        return "redirect:/recipe/"+recipeId+"/ingredients";
    }
}

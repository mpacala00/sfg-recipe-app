package pl.mpacala.sfgrecipieapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.mpacala.sfgrecipieapp.commands.RecipeCommand;
import pl.mpacala.sfgrecipieapp.services.RecipeService;

@RequestMapping("/recipe")
@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/show")
    public String getRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("/new")
    public String createRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeForm";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeForm";
    }

    //for recipeForm
    @PostMapping
    public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/"+ savedCommand.getId() +"/show/";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id:"+id);
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }
}

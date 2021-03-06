package pl.mpacala.sfgrecipieapp.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.mpacala.sfgrecipieapp.commands.RecipeCommand;
import pl.mpacala.sfgrecipieapp.exceptions.NotFoundException;
import pl.mpacala.sfgrecipieapp.services.RecipeService;

import javax.validation.Valid;

@RequestMapping("/recipe")
@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public static final String RECIPE_FORM_URL = "recipe/recipeForm";

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
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
                               BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(err -> {
                log.debug(err.toString());
            });

            return RECIPE_FORM_URL;
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/"+ savedCommand.getId() +"/show";
    }

    @GetMapping("/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("Deleting id:"+id);
        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    //overrides status of exception class, without it status code would be 200
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception ex) {
        log.error("Exception: "+ex.getMessage());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("404error");
        mav.addObject("exception", ex);

        return mav;

    }
}

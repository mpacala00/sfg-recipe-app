package pl.mpacala.sfgrecipieapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;

@Controller
public class IndexController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public IndexController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "index"})
    public String index(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        System.out.println(recipeRepository.findAll());
        System.out.println("message");
        return "index";
    }
}

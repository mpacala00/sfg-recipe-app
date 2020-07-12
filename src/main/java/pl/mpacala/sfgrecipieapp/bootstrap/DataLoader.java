package pl.mpacala.sfgrecipieapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.mpacala.sfgrecipieapp.model.*;
import pl.mpacala.sfgrecipieapp.repositories.CategoryRepository;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;
import pl.mpacala.sfgrecipieapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Ingredients and categories wouldn't show up because only their ids were persisted to recipe repo
 * what was necessary is adding Recipe field into both category and ingredient constructor
 */

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
                      UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        //retrieving optionals from database
        Optional<UnitOfMeasure> teaspoonOpt = unitOfMeasureRepository.findByUnit("Teaspoon");

        //pretty ugly code. Too bad!
        if(!teaspoonOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> tablespoonOpt = unitOfMeasureRepository.findByUnit("Tablespoon");

        if(!tablespoonOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> cupOpt = unitOfMeasureRepository.findByUnit("Cup");

        if(!cupOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> pinchOpt = unitOfMeasureRepository.findByUnit("Pinch");

        if(!pinchOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> ounceOpt = unitOfMeasureRepository.findByUnit("Ounce");

        if(!ounceOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> dashOpt = unitOfMeasureRepository.findByUnit("Dash");

        if(!dashOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> cloveOpt = unitOfMeasureRepository.findByUnit("Clove");

        if(!cloveOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> pintOpt = unitOfMeasureRepository.findByUnit("Pint");

        if(!pintOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        Optional<UnitOfMeasure> eachOpt = unitOfMeasureRepository.findByUnit("Each");

        if(!eachOpt.isPresent()) {
            throw new RuntimeException("Expected unit not found.");
        }

        //getting uom optionals
        UnitOfMeasure teaspoonUom = teaspoonOpt.get();
        UnitOfMeasure tablespoonUom = tablespoonOpt.get();
        UnitOfMeasure cupUom = cupOpt.get();
        UnitOfMeasure pinchUom = pinchOpt.get();
        UnitOfMeasure ounceUom = ounceOpt.get();
        UnitOfMeasure dashUom = dashOpt.get();
        UnitOfMeasure cloveUom = cloveOpt.get();
        UnitOfMeasure pintUom = pintOpt.get();
        UnitOfMeasure eachUom = eachOpt.get();

        //setting up the optionals for categories
        Optional<Category> americanOpt = categoryRepository.findByDescription("American");
        if(!americanOpt.isPresent()) {
            throw new RuntimeException("Expected category not found.");
        }

        Optional<Category> mexicanOpt = categoryRepository.findByDescription("Mexican");
        if(!mexicanOpt.isPresent()) {
            throw new RuntimeException("Expected category not found.");
        }

        //getting the categories
        Category americanCat = americanOpt.get();
        Category mexicanCat = mexicanOpt.get();

        Recipe guacamole = new Recipe();
        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDescription("Simple and easy way to prepare delicious guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(2);
        guacamole.setSource("simplyrecipes.com");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        guacamole.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacamoleNotes = new Notes();
        guacamoleNotes.setRecipeNotes("The best guacamole keeps it simple: just ripe avocados," +
                " salt, a squeeze of lime, onions, chiles, cilantro, and some chopped tomato." +
                " Serve it as a dip at your next party or spoon it on top of tacos for an easy" +
                " dinner upgrade.");

        //guacamoleNotes.setRecipe(guacamole); no longer needed
        guacamole.setNotes(guacamoleNotes);


        //setting ingredients
        guacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2), eachUom));
        guacamole.addIngredient(new Ingredient("kosher salt", new BigDecimal(.5), teaspoonUom));
        guacamole.addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(2), tablespoonUom));
        guacamole.addIngredient(new Ingredient("minced red onion", new BigDecimal(2), tablespoonUom));
        guacamole.addIngredient(new Ingredient("serrano chilli", new BigDecimal(2), eachUom));
        guacamole.addIngredient(new Ingredient("cilantro", new BigDecimal(2), tablespoonUom));
        guacamole.addIngredient(new Ingredient("black pepper", new BigDecimal(2), dashUom));
        guacamole.addIngredient(new Ingredient("ripe tomato", new BigDecimal(.5), eachUom));

        guacamole.getCategories().add(americanCat);
        guacamole.getCategories().add(mexicanCat);

        recipes.add(guacamole);

        return recipes;
    }

    @Override
    public void run(String... args) throws Exception {

        recipeRepository.saveAll(getRecipes());
        System.out.println("Recipes saved to repo");

    }
}

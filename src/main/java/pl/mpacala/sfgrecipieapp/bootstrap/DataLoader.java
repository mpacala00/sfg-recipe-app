package pl.mpacala.sfgrecipieapp.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.mpacala.sfgrecipieapp.model.Difficulty;
import pl.mpacala.sfgrecipieapp.model.Ingredient;
import pl.mpacala.sfgrecipieapp.model.Recipe;
import pl.mpacala.sfgrecipieapp.repositories.CategoryRepository;
import pl.mpacala.sfgrecipieapp.repositories.RecipeRepository;
import pl.mpacala.sfgrecipieapp.repositories.UnitOfMeasureRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public void run(String... args) throws Exception {

        Recipe guacamole = new Recipe();
        //Guac is a mexican recipe
        guacamole.getCategories().add(categoryRepository.findByDescription("Mexican").get());
        //use .get() when dealing with Optional<>

        //ingredient -> description, amount (BigDecimal), UnitOfMeasure uom
        Set<Ingredient> guacamoleIngredients = new HashSet<>();
        guacamoleIngredients.add(new Ingredient("ripe avocado", new BigDecimal(2), null));
        guacamoleIngredients.add(new Ingredient("salt", new BigDecimal(0.25),
                unitOfMeasureRepository.findByUnit("Teaspoon").get()));
        guacamoleIngredients.add(new Ingredient("fresh lime juice", new BigDecimal(1),
                unitOfMeasureRepository.findByUnit("Tablespoon").get()));
        guacamoleIngredients.add(new Ingredient("minced red onion", new BigDecimal(2),
                unitOfMeasureRepository.findByUnit("Tablespoon").get()));
        guacamoleIngredients.add(new Ingredient("minced serrano chili", new BigDecimal(1), null));
        guacamoleIngredients.add(new Ingredient("finely chopped silantro", new BigDecimal(2),
                unitOfMeasureRepository.findByUnit("Tablespoon").get()));
        guacamoleIngredients.add(new Ingredient("black pepper", new BigDecimal(1),
                unitOfMeasureRepository.findByUnit("Dash").get()));
        guacamoleIngredients.add(new Ingredient("ripe tomato", new BigDecimal(0.5), null));
        guacamoleIngredients.add(new Ingredient("red radish for garnish", new BigDecimal(1), null));
        guacamoleIngredients.add(new Ingredient("tortilla chips to serve", new BigDecimal(1), null));

        guacamole.setIngredients(guacamoleIngredients);

        guacamole.setDifficulty(Difficulty.EASY);
        guacamole.setDescription("Simple and easy way to prepare delicious guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(0);
        guacamole.setServings(2);
        guacamole.setSource("simplyrecipes.com");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacamole.setDirections("1 Cut the avocado, remove flesh: Cut the avocados in half." +
                " Remove the pit. Score the inside of the avocado with a blunt knife" +
                " and scoop out the flesh with a spoon." +
                " (See How to Cut and Peel an Avocado.) Place in a bowl.");
        //too long:
        /*
        * 2 Mash with a fork: Using a fork, roughly mash the avocado." +
                " (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "4 Serve: Serve immediately, or if making a few hours ahead," +
                " place plastic wrap on the surface of the guacamole and" +
                " press down to cover it and to prevent air reaching it." +
                " (The oxygen in the air causes oxidation which will turn the guacamole brown.)" +
                " Refrigerate until ready to serve.*/

        recipeRepository.save(guacamole);
        System.out.println("Recipes saved to repo");

    }
}

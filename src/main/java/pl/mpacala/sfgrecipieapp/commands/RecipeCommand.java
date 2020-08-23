package pl.mpacala.sfgrecipieapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mpacala.sfgrecipieapp.model.Difficulty;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();
}

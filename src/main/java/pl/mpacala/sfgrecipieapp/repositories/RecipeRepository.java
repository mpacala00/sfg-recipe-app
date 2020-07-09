package pl.mpacala.sfgrecipieapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mpacala.sfgrecipieapp.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}

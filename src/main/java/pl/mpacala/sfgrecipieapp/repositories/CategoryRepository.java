package pl.mpacala.sfgrecipieapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mpacala.sfgrecipieapp.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}

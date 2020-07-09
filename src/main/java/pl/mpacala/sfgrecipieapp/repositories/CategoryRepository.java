package pl.mpacala.sfgrecipieapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mpacala.sfgrecipieapp.model.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}

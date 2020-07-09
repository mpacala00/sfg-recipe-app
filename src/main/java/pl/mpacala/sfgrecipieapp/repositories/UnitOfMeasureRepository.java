package pl.mpacala.sfgrecipieapp.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.mpacala.sfgrecipieapp.model.UnitOfMeasure;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    //query method - does not require implementation thanks to JPA
    Optional<UnitOfMeasure> findByUnit(String unit);
}

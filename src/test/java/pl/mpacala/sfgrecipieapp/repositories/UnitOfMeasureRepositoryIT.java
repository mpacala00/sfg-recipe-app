package pl.mpacala.sfgrecipieapp.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.mpacala.sfgrecipieapp.model.UnitOfMeasure;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest //test data layer with embedded database
class UnitOfMeasureRepositoryIT {

    @Autowired //DI on the integration test
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    //testing for teaspoon
    @Test
    //reloads context (useful if we change some data in tests), takes a bit longer to do
    @DirtiesContext
    void findByUnit() {

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUnit("Teaspoon");

        assertEquals("Teaspoon", unitOfMeasure.get().getUnit());
    }

    //testing for cup
    @Test
    void findByUnitCup() {

        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUnit("Cup");

        assertEquals("Cup", unitOfMeasure.get().getUnit());
    }
}
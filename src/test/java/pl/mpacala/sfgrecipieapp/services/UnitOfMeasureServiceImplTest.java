package pl.mpacala.sfgrecipieapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.mpacala.sfgrecipieapp.commands.UnitOfMeasureCommand;
import pl.mpacala.sfgrecipieapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import pl.mpacala.sfgrecipieapp.model.UnitOfMeasure;
import pl.mpacala.sfgrecipieapp.repositories.UnitOfMeasureRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
                unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void testFindAll() {
        //given
        //these have to be commands, because unitOfMeasureService.findAll() returns set of uom commands
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom1.setId(2L);

        Set<UnitOfMeasure> unitSet = new HashSet<>();
        unitSet.add(uom1);
        unitSet.add(uom2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitSet);

        //when
        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.findAll();

        //then
        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}
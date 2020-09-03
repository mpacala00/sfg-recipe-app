package pl.mpacala.sfgrecipieapp.services;

import org.springframework.stereotype.Service;
import pl.mpacala.sfgrecipieapp.commands.UnitOfMeasureCommand;
import pl.mpacala.sfgrecipieapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import pl.mpacala.sfgrecipieapp.repositories.UnitOfMeasureRepository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand command;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureCommand command) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.command = command;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {

        //swap iterator for something that streams can be used against
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(command::convert)
                .collect(Collectors.toSet());
    }
}
